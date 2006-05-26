package corner.orm.tapestry.filter;

import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.tapestry.services.WebRequestServicer;
import org.apache.tapestry.services.WebRequestServicerFilter;
import org.apache.tapestry.web.WebRequest;
import org.apache.tapestry.web.WebResponse;
import org.hibernate.FlushMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.orm.hibernate3.SessionFactoryUtils;
import org.springframework.orm.hibernate3.SessionHolder;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.transaction.support.TransactionSynchronizationManager;

/**
 *
 * 针对hibernate的tapestry的每一个servlet一个请求。
 *
 * 借鉴了SpringFramework的OpenSessionInViewFilter
 *
 * @author Jun Tsai
 * @version $Revision$
 * @since 2006-5-26
 */
public class OneSessionPerServletRequestFilter implements
		WebRequestServicerFilter {
	/**
	 * Logger for this class
	 */
	private static final Log log = LogFactory
			.getLog(OneSessionPerServletRequestFilter.class);

	private boolean transactionPerRequest = true;

	private PlatformTransactionManager transactionManager;

	private SessionFactory sessionFactory;

	public void service(WebRequest request, WebResponse response,
			WebRequestServicer servicer) throws IOException {
		Session session = null;
		boolean participate = false;
		if (TransactionSynchronizationManager.hasResource(getSessionFactory())) {
			participate = true;
		} else {
			session = createSession(getSessionFactory());
			TransactionSynchronizationManager.bindResource(getSessionFactory(),
					new SessionHolder(session));
		}
		try {
			if (!participate && isTransactionPerRequest()) {
				final TransactionStatus txStat = transactionManager
						.getTransaction(new DefaultTransactionDefinition(
								TransactionDefinition.PROPAGATION_REQUIRED));
				try {
					servicer.service(request, response);
					if (!txStat.isRollbackOnly()) {
						log.debug("Committing transaction...");
						transactionManager.commit(txStat);
					} else {
						log
								.debug("Transaction marked as rollback-only, initiating rollback...");
						transactionManager.rollback(txStat);
					}
				} catch (IOException e) {
					log
							.debug(
									"An exception occurred during request processing, rolling back transaction...",
									e);
					transactionManager.rollback(txStat);
					throw e;
				} catch (RuntimeException e) {
					log
							.debug(
									"An exception occurred during request processing, rolling back transaction...",
									e);
					transactionManager.rollback(txStat);
					throw e;
				}
			} else {
				servicer.service(request, response);
			}
		} finally {
			if (!participate) {
				TransactionSynchronizationManager
						.unbindResource(getSessionFactory());
				try {
					closeSession(session, getSessionFactory());
				} catch (RuntimeException ex) {
					log
							.error(
									"Unexpected exception on closing Hibernate Session",
									ex);
				}
			}
		}
	}

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public boolean isTransactionPerRequest() {
		return transactionPerRequest;
	}

	public void setTransactionPerRequest(boolean transactionPerRequest) {
		this.transactionPerRequest = transactionPerRequest;
	}

	public void setTransactionManager(
			PlatformTransactionManager transactionManager) {
		this.transactionManager = transactionManager;
	}

	protected void closeSession(Session session, SessionFactory sessionFactory) {
		log.debug("Closing Hibernate Session...");
		SessionFactoryUtils.releaseSession(session, sessionFactory);
	}

	protected Session createSession(SessionFactory sessionFactory)
			throws DataAccessResourceFailureException {
		log.debug("Creating new Hibernate Session...");
		return createNewSession(sessionFactory);
	}

	private Session createNewSession(SessionFactory sessionFactory) {
		Session session = SessionFactoryUtils.getSession(sessionFactory, true);
		session.setFlushMode(FlushMode.NEVER);
		return session;
	}

}
