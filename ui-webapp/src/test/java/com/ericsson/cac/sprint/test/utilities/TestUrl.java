package com.ericsson.cac.sprint.test.utilities;

import java.net.URL;

import org.junit.Test;

public class TestUrl {

	@Test
	public void testUrl() {
		try {
			URL url = new URL("http://144.229.209.144:8080/primary/tetet");
			System.out.println(url);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
