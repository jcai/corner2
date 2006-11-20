package corner.study.proxy.model;

public class FooImpl implements Foo {

	public String getSayHello() {
		System.out.println("from implementor");
		return "hello";
	}

}
