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

	@Pointcut("@within(annotation)")
	public void onClass(Logged annotation) {
	}

	@Pointcut("@annotation(annotation)")
	public void onMethod(Logged annotation) {
	}

//	@Pointcut("execution(* org.springframework.data.repository.CrudRepository+.*(..))")
//	@Pointcut("execution(* org.springframework.data.repository.Repository+.*(..))")
//	@Pointcut("this(org.springframework.data.repository.Repository)")
//	public void repository() {
//	}

	@Around(value = "onClass(annotation)", argNames = "joinPoint,annotation")
	public Object logMethodCallOnClass(ProceedingJoinPoint joinPoint, Logged annotation) throws Throwable {
		return process(joinPoint, annotation.value());
	}

	@Around(value = "onMethod(annotation)", argNames = "joinPoint,annotation")
	public Object logMethodCallOnMethod(ProceedingJoinPoint joinPoint, Logged annotation) throws Throwable {
		return process(joinPoint, annotation.value());
	}

//	@Around(value = "repository()")
//	public Object logMethodCallOnRepository(ProceedingJoinPoint joinPoint) throws Throwable {
//		return process(joinPoint, Logged.LogLevel.debug);
//	}

	private Object process(ProceedingJoinPoint joinPoint, Logged.LogLevel logLevel) throws Throwable {
		Logger log = LoggerFactory.getLogger(joinPoint.getSignature().getDeclaringType());
		if (isDisabled(logLevel, log)) {
			return joinPoint.proceed();
		}
		long start = System.currentTimeMillis();
		logStart(joinPoint, logLevel, log);
		Object returnValue;
		try {
			returnValue = joinPoint.proceed();
			logEnd(joinPoint, logLevel, log, start, returnValue);
			return returnValue;
		} catch (Throwable t) {
			logThrow(joinPoint, logLevel, log, start, t);
			throw t;
		}
	}

	private void logStart(ProceedingJoinPoint joinPoint, Logged.LogLevel logLevel, Logger log) {
		String indent = isInfo(logLevel) ? ">>" : ">";
		String methodName = joinPoint.getSignature().getName();
		Object[] methodArguments = joinPoint.getArgs();
		String message = "{} [ENTRY] {}({})";
		Object[] messageArguments = new Object[2 + methodArguments.length];
		messageArguments[0] = indent;
		messageArguments[1] = methodName;
		System.arraycopy(methodArguments, 0, messageArguments, 2, methodArguments.length);
		if (isInfo(logLevel)) {
			log.info(message, messageArguments);
		} else {
			log.debug(message, messageArguments);
		}
	}

	private void logEnd(ProceedingJoinPoint joinPoint, Logged.LogLevel logLevel, Logger log, long start,
			Object returnValue) {
		String indent = isInfo(logLevel) ? "<<" : "<";
		String methodName = joinPoint.getSignature().getName();
		String message = "{} [EXIT] {} @ {} ms: {}";
		Object[] messageArguments = new Object[]{indent, methodName, duration(start), returnValue};
		if (isInfo(logLevel)) {
			log.info(message, messageArguments);
		} else {
			log.debug(message, messageArguments);
		}
	}

	private void logThrow(ProceedingJoinPoint joinPoint, Logged.LogLevel logLevel, Logger log, long start,
			Throwable t) {
		String indent = isInfo(logLevel) ? "<<" : "<";
		String methodName = joinPoint.getSignature().getName();
		String message = "{} [THROW] {} @ {} ms: {}: {}";
		Object[] messageArguments = new Object[]
				{indent, methodName, duration(start), t.getClass().getName(), t.getMessage()};
		if (isInfo(logLevel)) {
			log.info(message, messageArguments);
		} else {
			log.debug(message, messageArguments);
		}
	}

	private boolean isDisabled(Logged.LogLevel logLevel, Logger log) {
		return (isInfo(logLevel) && !log.isInfoEnabled())
				|| !log.isDebugEnabled();
	}

	private boolean isInfo(Logged.LogLevel logLevel) {
		return Logged.LogLevel.info.equals(logLevel);
	}

	private long duration(long start) {
		return System.currentTimeMillis() - start;
	}
}
