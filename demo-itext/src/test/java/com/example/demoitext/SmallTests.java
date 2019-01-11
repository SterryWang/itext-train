package com.example.demoitext;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class SmallTests {

	@Test
	public void test1() {
		List< Integer>  list = new ArrayList<Integer>(3);
		list.add(1);
		list.add(2);
		list.add(3);
		
		
		List<Integer>  list2=list;
		list.set(0, 4);
		for(int i=0;i<list.size();i++) {
			System.out.println(list.get(i));

			System.out.println(list2.get(i));
		}
		
		
	//System.out.println(list.size());
	}
}
