package corner.orm.hibernate.event;

import org.easymock.EasyMock;
import org.hibernate.event.PostLoadEvent;
import org.testng.annotations.Test;

import corner.orm.hibernate.IOldObjectAccessable;

public class InitOldObjectListenerTest {

	@Test
	public void testInitOldObject(){
		InitOldObjectListener listener=new InitOldObjectListener();
		PostLoadEvent event=new PostLoadEvent(null);
		IOldObjectAccessable obj=EasyMock.createMock(IOldObjectAccessable.class);
		obj.initOldObject();
		event.setEntity(obj);
		EasyMock.replay(obj);
		listener.onPostLoad(event);
		EasyMock.verify(obj);
		
	}
}
