package org.springframework.cglib.proxy;

/**
 * @author chenkh
 * @time 2020/10/18 9:56
 */
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import net.sf.cglib.core.Constants;
import net.sf.cglib.core.Signature;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;

class BridgeMethodResolver {
    private final Map declToBridge;
    private final ClassLoader classLoader;

    public BridgeMethodResolver(Map declToBridge, ClassLoader classLoader) {
        this.declToBridge = declToBridge;
        this.classLoader = classLoader;
    }

    public Map resolveAll() {
        Map resolved = new HashMap();
        Iterator entryIter = this.declToBridge.entrySet().iterator();

        while(entryIter.hasNext()) {
            Entry entry = (Entry)entryIter.next();
            Class owner = (Class)entry.getKey();
            Set bridges = (Set)entry.getValue();

            try {
                InputStream is = this.classLoader.getResourceAsStream(owner.getName().replace('.', '/') + ".class");
                if (is == null) {
                    return resolved;
                }

                try {
                    (new ClassReader(is)).accept(new BridgeMethodResolver.BridgedFinder(bridges, resolved), 6);
                } finally {
                    is.close();
                }
            } catch (IOException var11) {
            }
        }

        return resolved;
    }

    private static class BridgedFinder extends ClassVisitor {
        private Map resolved;
        private Set eligibleMethods;
        private Signature currentMethod = null;

        BridgedFinder(Set eligibleMethods, Map resolved) {
            super(Constants.ASM_API);
            this.resolved = resolved;
            this.eligibleMethods = eligibleMethods;
        }

        public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
        }

        public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
            Signature sig = new Signature(name, desc);
            if (this.eligibleMethods.remove(sig)) {
                this.currentMethod = sig;
                return new MethodVisitor(Constants.ASM_API) {
                    public void visitMethodInsn(int opcode, String owner, String name, String desc, boolean itf) {
                        if ((opcode == 183 || itf && opcode == 185) && BridgeMethodResolver.BridgedFinder.this.currentMethod != null) {
                            Signature target = new Signature(name, desc);
                            if (!target.equals(BridgeMethodResolver.BridgedFinder.this.currentMethod)) {
                               BridgeMethodResolver.BridgedFinder.this.resolved.put(BridgeMethodResolver.BridgedFinder.this.currentMethod, target);
                            }

                            BridgeMethodResolver.BridgedFinder.this.currentMethod = null;
                        }

                    }
                };
            } else {
                return null;
            }
        }
    }
}
