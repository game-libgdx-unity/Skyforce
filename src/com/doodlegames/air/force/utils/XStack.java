package com.doodlegames.air.force.utils;

import java.util.Stack;

public class XStack<E extends Object> extends Stack<E> {

   private static final long serialVersionUID = 1L;


   public XStack() {}

   public XStack(int var1) {
      super.ensureCapacity(var1);
   }
}
