package me.giskard.dust.forge;

import java.lang.reflect.Method;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.CtNewMethod;
import javassist.NotFoundException;

public class GiskardForgeTest implements GiskardForgeConsts {

	public static void main(String[] args) throws Exception {
		ClassPool pool = ClassPool.getDefault();

		CtClass cc = pool.get("me.giskard.dust.forge.JassistTest");
		try {
			cc.getDeclaredMethod("g");
			System.out.println("g() is already defined in sample.Test.");
		} catch (NotFoundException e) {
			/*
			 * getDeclaredMethod() throws an exception if g() is not defined in sample.Test.
			 */
			CtMethod fMethod = cc.getDeclaredMethod("f");
			CtMethod gMethod = CtNewMethod.copy(fMethod, "g", cc, null);
			cc.addMethod(gMethod);

			cc.defrost();
			
			gMethod = cc.getDeclaredMethod("g");
			
			gMethod.insertBefore("{System.out.println(\"pukkpukk \" + i);}");
			
			cc.writeFile(); // update the class file
			System.out.println("g() was added.");
			
			Class zz = cc.toClass();
			Object o = zz.newInstance();
			Method m = zz.getDeclaredMethod("g", int.class);
			m.invoke(o, 9);
		}
	}
}
