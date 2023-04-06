package org.springframework.cglib.proxy;

/**
 * @author chenkh
 * @time 2020/10/18 9:55
 */
import java.util.Iterator;
import java.util.List;
import net.sf.cglib.core.Block;
import net.sf.cglib.core.ClassEmitter;
import net.sf.cglib.core.CodeEmitter;
import net.sf.cglib.core.EmitUtils;
import net.sf.cglib.core.MethodInfo;
import net.sf.cglib.core.Signature;
import net.sf.cglib.core.TypeUtils;
import org.objectweb.asm.Type;

class InvocationHandlerGenerator implements CallbackGenerator {
    public static final InvocationHandlerGenerator INSTANCE = new InvocationHandlerGenerator();
    private static final Type INVOCATION_HANDLER = TypeUtils.parseType("net.sf.cglib.proxy.InvocationHandler");
    private static final Type UNDECLARED_THROWABLE_EXCEPTION = TypeUtils.parseType("net.sf.cglib.proxy.UndeclaredThrowableException");
    private static final Type METHOD = TypeUtils.parseType("java.lang.reflect.Method");
    private static final Signature INVOKE = TypeUtils.parseSignature("Object invoke(Object, java.lang.reflect.Method, Object[])");

    InvocationHandlerGenerator() {
    }

    public void generate(ClassEmitter ce, Context context, List methods) {
        Iterator it = methods.iterator();

        while(it.hasNext()) {
            MethodInfo method = (MethodInfo)it.next();
            Signature impl = context.getImplSignature(method);
            ce.declare_field(26, impl.getName(), METHOD, (Object)null);
            CodeEmitter e = context.beginMethod(ce, method);
            Block handler = e.begin_block();
            context.emitCallback(e, context.getIndex(method));
            e.load_this();
            e.getfield(impl.getName());
            e.create_arg_array();
            e.invoke_interface(INVOCATION_HANDLER, INVOKE);
            e.unbox(method.getSignature().getReturnType());
            e.return_value();
            handler.end();
            EmitUtils.wrap_undeclared_throwable(e, handler, method.getExceptionTypes(), UNDECLARED_THROWABLE_EXCEPTION);
            e.end_method();
        }

    }

    public void generateStatic(CodeEmitter e, Context context, List methods) {
        Iterator it = methods.iterator();

        while(it.hasNext()) {
            MethodInfo method = (MethodInfo)it.next();
            EmitUtils.load_method(e, method);
            e.putfield(context.getImplSignature(method).getName());
        }

    }
}

