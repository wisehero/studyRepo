package network.tcp.autocloseable;

public class ResourceCloseMainV2 {
	public static void main(String[] args) {
		try {
			logic();
		} catch (CallException e) {
			System.out.println("CallException 예외 처리");
			e.printStackTrace();
		} catch (CloseException e) {
			System.out.println("CloseException 예외 처리");
			e.printStackTrace();
		}
	}

	private static void logic() throws CallException, CloseException {
		ResourceV1 resource1 = null;
		ResourceV1 resource2 = null;

		try {
			resource1 = new ResourceV1("resource1");
			resource2 = new ResourceV1("resource2");

			resource1.call();
			resource2.callEx(); // CallException
		} catch (CallException e) {
			System.out.println("ex: " + e);
			throw e;
		} finally {
			if (resource2 != null) {
				resource2.closeEx(); // 자원을 닫는 도중 또 예외가 터짐
			}
			if (resource1 != null) {
				resource1.closeEx(); // 여기서 호출이 안됨
			}
		}
	}
}
