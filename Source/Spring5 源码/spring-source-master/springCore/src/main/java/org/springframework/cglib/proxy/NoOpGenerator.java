package org.springframework.cglib.proxy;

/**
 * @author chenkh
 * @time 2020/10/18 9:48
 */
import java.util.Iterator;
import java.util.List;
import net.sf.cglib.core.ClassEmitter;
import net.sf.cglib.core.CodeEmitter;
import net.sf.cglib.core.EmitUtils;
import net.sf.cglib.core.MethodInfo;
import net.sf.cglib.core.TypeUtils;

public class NoOpGenerator implements CallbackGenerator {
    public static final NoOpGenerator INSTANCE = new NoOpGenerator();

    NoOpGenerator() {
    }

    public void generate(ClassEmitter ce, Context context, List methods) {
        Iterator it = methods.iterator();

        while(true) {
            MethodInfo method;
            do {
                if (!it.hasNext()) {
                    return;
                }

                method = (MethodInfo)it.next();
            } while(!TypeUtils.isBridge(method.getModifiers()) && (!TypeUtils.isProtected(context.getOriginalModifiers(method)) || !TypeUtils.isPublic(method.getModifiers())));

            CodeEmitter e = EmitUtils.begin_method(ce, method);
            e.load_this();
            context.emitLoadArgsAndInvoke(e, method);
            e.return_value();
            e.end_method();
        }
    }

    public void generateStatic(CodeEmitter e, Context context, List methods) {
    }
}
