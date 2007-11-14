// Copyright 2007 the original author or authors.
// site: http://www.bjmaxinfo.com
// file: $Id$
// created at:2006-05-26
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

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
 * @see org.springframework.orm.hibernate3.support.OpenSessionInViewFilter
 */
public class OneSessionPerServletRequestFilter implements
		WebRequestServicerFilter {
	/**
	 * Logger for this class
	 */
	private static final Log log = LogFactory
			.getLog(OneSessionPerServletRequestFilter.class);

	private boolean transactionPerRequest = true;

	//private PlatformTransactionManager transactionManager;

	private SessionFactory sessionFactory;

	public void service(WebRequest request, WebResponse response,
			WebRequestServicer servicer) throws IOException {
//		String pathInfo=request.getPathInfo();
		if(request.getActivationPath().startsWith("/assets")){ //过滤掉对assets内容进行open session in view
			servicer.service(request, response);
			return;
		}
		Session session = null;
		boolean participate = false;

		if (isSingleSession()) {
			
			// single session mode
			if (TransactionSynchronizationManager.hasResource(sessionFactory)) {
				// Do not modify the Session: just set the participate flag.
				participate = true;
			} else {
				log
						.debug("Opening single Hibernate Session in OpenSessionInViewFilter");
				session = getSession(sessionFactory);
				TransactionSynchronizationManager.bindResource(sessionFactory,
						new SessionHolder(session));
			}
		} else {
			// deferred close mode
			if (SessionFactoryUtils.isDeferredCloseActive(sessionFactory)) {
				// Do not modify deferred close: just set the participate flag.
				participate = true;
			} else {
				SessionFactoryUtils.initDeferredClose(sessionFactory);
			}
		}

		try {
			servicer.service(request, response);
		}

		finally {
			if (!participate) {
				if (isSingleSession()) {
					// single session mode
					TransactionSynchronizationManager
							.unbindResource(sessionFactory);
					log
							.debug("Closing single Hibernate Session in OpenSessionInViewFilter");
					try {
						closeSession(session, sessionFactory);
					} catch (RuntimeException ex) {
						log
								.error(
										"Unexpected exception on closing Hibernate Session",
										ex);
					}
				} else {
					// deferred close mode
					SessionFactoryUtils.processDeferredClose(sessionFactory);
				}
			}
		}
	}

	private boolean isSingleSession() {

		return true;
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
		//this.transactionManager = transactionManager;
	}

	/**
	 * Get a Session for the SessionFactory that this filter uses. Note that
	 * this just applies in single session mode!
	 * <p>
	 * The default implementation delegates to SessionFactoryUtils' getSession
	 * method and sets the Session's flushMode to NEVER.
	 * <p>
	 * Can be overridden in subclasses for creating a Session with a custom
	 * entity interceptor or JDBC exception translator.
	 * 
	 * @param sessionFactory
	 *            the SessionFactory that this filter uses
	 * @return the Session to use
	 * @throws DataAccessResourceFailureException
	 *             if the Session could not be created
	 * @see org.springframework.orm.hibernate3.SessionFactoryUtils#getSession(SessionFactory,
	 *      boolean)
	 * @see org.hibernate.FlushMode#NEVER
	 */
	protected Session getSession(SessionFactory sessionFactory)
			throws DataAccessResourceFailureException {
		Session session = SessionFactoryUtils.getSession(sessionFactory, true);
		//TODO 确定NEVER的差别
		session.setFlushMode(FlushMode.AUTO);
		return session;
	}

	/**
	 * Close the given Session. Note that this just applies in single session
	 * mode!
	 * <p>
	 * The default implementation delegates to SessionFactoryUtils'
	 * releaseSession method.
	 * <p>
	 * Can be overridden in subclasses, e.g. for flushing the Session before
	 * closing it. See class-level javadoc for a discussion of flush handling.
	 * Note that you should also override getSession accordingly, to set the
	 * flush mode to something else than NEVER.
	 * 
	 * @param session
	 *            the Session used for filtering
	 * @param sessionFactory
	 *            the SessionFactory that this filter uses
	 */
	protected void closeSession(Session session, SessionFactory sessionFactory) {
		SessionFactoryUtils.releaseSession(session, sessionFactory);
	}

	
}
