package hello.aspectj;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

@SuppressWarnings("MissingAspectjAutoproxyInspection")
@Aspect
public abstract class AbstractMethodExecutionAspect {
    @Pointcut
    public abstract void methodExecution();

    @SuppressWarnings("NoopMethodInAbstractClass")
    @Pointcut(
            "execution(int compareTo(Object)) "
                    + "|| execution(boolean equals(Object)) "
                    + "|| execution(int hashCode()) "
                    + "|| execution(String toString()) ")
    public void trivialMethodExecution() {}

    @Pointcut("within(@org.aspectj.lang.annotation.Aspect *)")
    public void withinAspect() {}

    @Before("methodExecution() && !trivialMethodExecution() && !withinAspect()")
    public void registerInvocation(JoinPoint.StaticPart thisJointPoint) {
        InvocationRegistry.registerMethodInvocation(thisJointPoint.getSignature());
    }
}
