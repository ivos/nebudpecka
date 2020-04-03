package cz.lastware.nebudpecka.config;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

	@Pointcut("execution(* cz.lastware.nebudpecka..*.*(..))")
	public void app() {
	}

	@Pointcut("app() && @within(annotation)")
	public void onClass(Logged annotation) {
	}

	@Pointcut("app() && @annotation(annotation)")
	public void onMethod(Logged annotation) {
	}

	@Around(value = "onClass(annotation)", argNames = "joinPoint,annotation")
	public Object logMethodCallOnClass(ProceedingJoinPoint joinPoint, Logged annotation) throws Throwable {
		return process(joinPoint, annotation);
	}

	@Around(value = "onMethod(annotation)", argNames = "joinPoint,annotation")
	public Object logMethodCallOnMethod(ProceedingJoinPoint joinPoint, Logged annotation) throws Throwable {
		return process(joinPoint, annotation);
	}

	private Object process(ProceedingJoinPoint joinPoint, Logged annotation) throws Throwable {
		Logger log = LoggerFactory.getLogger(joinPoint.getSignature().getDeclaringType());
		logStart(joinPoint, annotation, log);
		Object returnValue;
		try {
			returnValue = joinPoint.proceed();
			logEnd(joinPoint, annotation, log, returnValue);
			return returnValue;
		} catch (Throwable t) {
			logThrow(joinPoint, annotation, log, t);
			throw t;
		}
	}

	private void logStart(ProceedingJoinPoint joinPoint, Logged annotation, Logger log) {
		if (isDisabled(annotation, log)) {
			return;
		}
		String indent = isInfo(annotation) ? ">>" : ">";
		String methodName = joinPoint.getSignature().getName();
		Object[] methodArguments = joinPoint.getArgs();
		String message = "{} [ENTRY] {}({})";
		Object[] messageArguments = new Object[2 + methodArguments.length];
		messageArguments[0] = indent;
		messageArguments[1] = methodName;
		System.arraycopy(methodArguments, 0, messageArguments, 2, methodArguments.length);
		if (isInfo(annotation)) {
			log.info(message, messageArguments);
		} else {
			log.debug(message, messageArguments);
		}
	}

	private void logEnd(ProceedingJoinPoint joinPoint, Logged annotation, Logger log, Object returnValue) {
		if (isDisabled(annotation, log)) {
			return;
		}
		String indent = isInfo(annotation) ? "<<" : "<";
		String methodName = joinPoint.getSignature().getName();
		String message = "{} [EXIT] {}: {}";
		if (isInfo(annotation)) {
			log.info(message, indent, methodName, returnValue);
		} else {
			log.debug(message, indent, methodName, returnValue);
		}
	}

	private void logThrow(ProceedingJoinPoint joinPoint, Logged annotation, Logger log, Throwable t) {
		if (isDisabled(annotation, log)) {
			return;
		}
		String indent = isInfo(annotation) ? "<<" : "<";
		String methodName = joinPoint.getSignature().getName();
		String message = "{} [THROW] {}: {}";
		if (isInfo(annotation)) {
			log.info(message, indent, methodName, t);
		} else {
			log.debug(message, indent, methodName, t);
		}
	}

	private boolean isDisabled(Logged annotation, Logger log) {
		return (isInfo(annotation) && !log.isInfoEnabled())
				|| !log.isDebugEnabled();
	}

	private boolean isInfo(Logged annotation) {
		return Logged.LogLevel.info.equals(annotation.value());
	}
}
