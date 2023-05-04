package org.springframework.cglib.proxy;

/**
 * @author chenkh
 * @time 2020/10/18 9:46
 */
import java.util.List;
import net.sf.cglib.core.ClassEmitter;
import net.sf.cglib.core.CodeEmitter;
import net.sf.cglib.core.MethodInfo;
import net.sf.cglib.core.Signature;

public interface CallbackGenerator {
    void generate(ClassEmitter var1, Context var2, List var3) throws Exception;

    void generateStatic(CodeEmitter var1, Context var2, List var3) throws Exception;

    public interface Context {
        ClassLoader getClassLoader();

        CodeEmitter beginMethod(ClassEmitter var1, MethodInfo var2);

        int getOriginalModifiers(MethodInfo var1);

        int getIndex(MethodInfo var1);

        void emitCallback(CodeEmitter var1, int var2);

        Signature getImplSignature(MethodInfo var1);

        void emitLoadArgsAndInvoke(CodeEmitter var1, MethodInfo var2);
    }
}
