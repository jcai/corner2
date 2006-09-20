//==============================================================================
// file :       $Id$
// project:     corner
//
// last change: date:       $Date$
//              by:         $Author$
//              revision:   $Revision$
//------------------------------------------------------------------------------
//copyright:	Beijing Maxinfo Technology Ltd. http://www.bjmaxinfo.com
//License:      the Apache License, Version 2.0 (the "License")
//==============================================================================

package corner.orm.tapestry.record;

import static org.easymock.EasyMock.expect;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.apache.tapestry.BaseComponentTestCase;
import org.apache.tapestry.IPage;
import org.apache.tapestry.IRequestCycle;
import org.apache.tapestry.engine.ServiceEncoding;
import org.apache.tapestry.record.ClientPropertyPersistenceScope;
import org.apache.tapestry.record.PageClientPropertyPersistenceScope;
import org.apache.tapestry.record.PersistentPropertyDataEncoder;
import org.apache.tapestry.record.PersistentPropertyDataEncoderImpl;
import org.apache.tapestry.record.PropertyChange;
import org.apache.tapestry.record.PropertyChangeImpl;
import org.apache.tapestry.services.DataSqueezer;
import org.apache.tapestry.web.WebRequest;
import org.easymock.EasyMock;
import org.testng.annotations.Test;

/**
 * @author Jun Tsai
 * @version $Revision$
 * @since 2.1.1
 */
@Test
public class HibernateClientPropertyPersistenceStrategyTest extends BaseComponentTestCase{
	private PersistentPropertyDataEncoder newEncoder()
    {
        PersistentPropertyDataEncoderImpl encoder = new PersistentPropertyDataEncoderImpl();
        encoder.setClassResolver(getClassResolver());

        return encoder;
    }

    private ClientPropertyPersistenceScope newScope()
    {
        return newMock(ClientPropertyPersistenceScope.class);
    }

    public void testAddParametersForPersistentProperties()
    {
        WebRequest request = newRequest();

        ServiceEncoding encoding = newMock(ServiceEncoding.class);

        trainGetParameterNames(request, new String[]
        { "bar", "entityapp:MyPage" });

        trainGetParameterValue(request, "entityapp:MyPage", "ENCODED");

        encoding.setParameterValue("entityapp:MyPage", "ENCODED");

        replay();

        HibernateClientPropertyPersistenceStrategy strategy = new HibernateClientPropertyPersistenceStrategy();
        strategy.setRequest(request);
        strategy.setScope(new EntityAppClientPropertyPersistenceScope());
        strategy.setEncoder(newEncoder());

        strategy.initializeService();

        strategy.addParametersForPersistentProperties(encoding, false);

        verify();
    }

    public void testGetChangesUnknownPage()
    {
    	HibernateClientPropertyPersistenceStrategy strategy = new HibernateClientPropertyPersistenceStrategy();

        assertTrue(strategy.getStoredChanges("UnknownPage").isEmpty());
    }

    public void testInitialize()
    {
    	Object obj=new Object();//hibernate persistence object
    	
        WebRequest request = newRequest();
        DataSqueezer squeezer=newMock(DataSqueezer.class);
        
        ClientPropertyPersistenceScope scope = newScope();
        PersistentPropertyDataEncoder encoder = newMock(PersistentPropertyDataEncoder.class);

        trainGetParameterNames(request, new String[]
        { "foo", "entity:MyPage" });

        trainIsParameterForScope(scope, "foo", false);
        trainIsParameterForScope(scope, "entity:MyPage", true);

        trainExtractPageName(scope, "entity:MyPage", "MyPage");

        trainGetParameterValue(request, "entity:MyPage", "ENCODED");

        List<PropertyChangeImpl> changes = new ArrayList<PropertyChangeImpl>();
        changes.add(new PropertyChangeImpl("foo", "bar", "HB:clasname::idkey"));
        
        trainDecodePageChanges(encoder, "ENCODED", changes);
        
        EasyMock.expect(squeezer.unsqueeze("HB:clasname::idkey")).andReturn(obj);
        

        replay();

        HibernateClientPropertyPersistenceStrategy strategy = new HibernateClientPropertyPersistenceStrategy();
        strategy.setDataSqueezer(squeezer);
        
        strategy.setRequest(request);
        strategy.setScope(scope);
        strategy.setEncoder(encoder);

        strategy.initializeService();

        assertEquals(Collections.singletonList(new PropertyChangeImpl("foo", "bar",obj)), strategy.getStoredChanges("MyPage"));

        verify();
    }

    public void testPageScope()
    {
        WebRequest request = newRequest();
        IRequestCycle cycle = newCycle();
        IPage page = newPage();

        ServiceEncoding encoding = newMock(ServiceEncoding.class);
        
        trainGetParameterNames(request, new String[] { "foo", "entity:MyPage", "entity:OtherPage" });
        
        trainGetParameterValue(request, "entity:MyPage", "ENCODED1");
        trainGetParameterValue(request, "entity:OtherPage", "ENCODED2");
       
        trainGetPage(cycle, page);
        trainGetPageName(page, "MyPage");
        
        encoding.setParameterValue("entity:MyPage", "ENCODED1");

        trainGetPage(cycle, page);
        trainGetPageName(page, "MyPage");


        replay();

        EntityPageClientPropertyPersistenceScope scope = new EntityPageClientPropertyPersistenceScope();
        scope.setRequestCycle(cycle);

        HibernateClientPropertyPersistenceStrategy strategy = new HibernateClientPropertyPersistenceStrategy();
        strategy.setRequest(request);
        strategy.setScope(scope);
        strategy.setEncoder(newEncoder());

        strategy.initializeService();

        strategy.addParametersForPersistentProperties(encoding, false);

        verify();

    }

    public void testStoreAndRetrieve()
    {
    	Object obj=new Object();//hibernate persistence object
        PropertyChange pc = new PropertyChangeImpl("foo", "bar", obj);
        DataSqueezer squeezer=newMock(DataSqueezer.class);
        EasyMock.expect(squeezer.squeeze(obj)).andReturn("HB:classname::IDKEY");
        EasyMock.expect(squeezer.unsqueeze("HB:classname::IDKEY")).andReturn(obj);
        replay();
        
        HibernateClientPropertyPersistenceStrategy strategy = new HibernateClientPropertyPersistenceStrategy();
        strategy.setEncoder(newEncoder());
        strategy.setDataSqueezer(squeezer);
        
        strategy.store("MyPage", "foo", "bar", obj);

        assertEquals(Collections.singletonList(pc), strategy.getStoredChanges("MyPage"));

        strategy.discardStoredChanges("MyPage");

        assertEquals(Collections.EMPTY_LIST, strategy.getStoredChanges("MyPage"));
        verify();
    }

    private void trainDecodePageChanges(PersistentPropertyDataEncoder encoder, String encoded,
            List changes)
    {
        expect(encoder.decodePageChanges(encoded)).andReturn(changes);
    }

    private void trainExtractPageName(ClientPropertyPersistenceScope scope, String parameterName,
            String pageName)
    {
        expect(scope.extractPageName(parameterName)).andReturn(pageName);
    }

    private void trainGetPage(IRequestCycle cycle, IPage page)
    {
        expect(cycle.getPage()).andReturn(page);
    }

    private void trainGetParameterNames(WebRequest request, String[] names)
    {
        expect(request.getParameterNames()).andReturn(Arrays.asList(names));
    }

    private void trainGetParameterValue(WebRequest request, String parameterName, String value)
    {
        expect(request.getParameterValue(parameterName)).andReturn(value);
    }

    private void trainIsParameterForScope(ClientPropertyPersistenceScope scope,
            String parameterName, boolean result)
    {
        expect(scope.isParameterForScope(parameterName)).andReturn(result);
    }
}
