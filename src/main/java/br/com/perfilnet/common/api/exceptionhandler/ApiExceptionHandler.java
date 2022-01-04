package br.com.perfilnet.common.api.exceptionhandler;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolationException;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.TypeMismatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.fasterxml.jackson.databind.JsonMappingException.Reference;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.exc.PropertyBindingException;

import br.com.perfilnet.common.api.model.Problem;
import br.com.perfilnet.common.api.model.ProblemObject;
import br.com.perfilnet.common.api.model.ProblemType;
import br.com.perfilnet.common.domain.BusinessException;
import br.com.perfilnet.common.domain.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

	public static final String END_USER_GENERIC_MESSAGE = "Ocorreu um erro interno inesperado no sistema. Tente novamente e se o problema persistir, entre em contato com o administrador do sistema";

	@Autowired
	private MessageSource messageSource;

	@ExceptionHandler(Exception.class)
	public ResponseEntity<Object> handleUncaught(Exception ex, WebRequest request) {
		var status = HttpStatus.INTERNAL_SERVER_ERROR;
		var problemType = ProblemType.SYSTEM_ERROR;
		var detail = END_USER_GENERIC_MESSAGE;

		log.error(ex.getMessage(), ex);

		var problem = createProblemBuilder(status, problemType, detail).userMessage(detail).build();

		return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
	}

	@ExceptionHandler(BusinessException.class)
	public ResponseEntity<Object> handleBusinessException(BusinessException ex, WebRequest request) {
		var status = HttpStatus.BAD_REQUEST;
		var problemType = ProblemType.BUSINESS_ERROR;
		var detail = ex.getMessage();
		var problem = createProblemBuilder(status, problemType, detail).userMessage(detail).build();

		return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
	}

	@ExceptionHandler(EntityNotFoundException.class)
	public ResponseEntity<Object> handleEntityNotFoundException(EntityNotFoundException ex, WebRequest request) {
		return handleNotFoundException(ex, request);
	}

	@Override
	protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers,
			HttpStatus status, WebRequest request) {

		if (ex instanceof MissingServletRequestParameterException) {
			return handleMissing((MissingServletRequestParameterException) ex, headers, request);
		} else if (ex instanceof ConstraintViolationException) {
			return handleConstraint((ConstraintViolationException) ex, headers, request);
		} else if (body == null) {
			body = Problem.builder().timestamp(OffsetDateTime.now()).title(status.getReasonPhrase())
					.status(status.value()).userMessage(END_USER_GENERIC_MESSAGE).build();
		} else if (body instanceof String) {
			body = Problem.builder().timestamp(OffsetDateTime.now()).title(String.valueOf(body)).status(status.value())
					.userMessage(END_USER_GENERIC_MESSAGE).build();
		}

		return super.handleExceptionInternal(ex, body, headers, status, request);
	}

	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		var rootCause = ExceptionUtils.getRootCause(ex);

		if (rootCause instanceof InvalidFormatException) {
			return handleInvalidFormat((InvalidFormatException) rootCause, headers, status, request);
		} else if (rootCause instanceof PropertyBindingException) {
			return handlePropertyBinding((PropertyBindingException) rootCause, headers, status, request);
		}

		var problemType = ProblemType.INCOMPREHENSIBLE_MESSAGE;
		var detail = "O corpo da requisição está inválido. Verifique erro de sintaxe.";
		var problem = createProblemBuilder(status, problemType, detail).userMessage(END_USER_GENERIC_MESSAGE).build();

		return handleExceptionInternal(ex, problem, headers, status, request);
	}

	@Override
	protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request) {

		if (ex instanceof MethodArgumentTypeMismatchException) {
			var mismatchException = (MethodArgumentTypeMismatchException) ex;
			return handleMethodArgumentTypeMismatchException(mismatchException, headers, status, request);
		}

		return super.handleTypeMismatch(ex, headers, status, request);
	}

	@Override
	protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request) {

		var problemType = ProblemType.RESOURCE_NOT_FOUND;
		var detail = String.format("O recurso %s, que você tentou acessar, é inexistente.", ex.getRequestURL());
		var problem = createProblemBuilder(status, problemType, detail).userMessage(END_USER_GENERIC_MESSAGE).build();

		return handleExceptionInternal(ex, problem, headers, status, request);
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

		return handleValidationInternal(ex, headers, status, request, ex.getBindingResult());
	}

	@Override
	protected ResponseEntity<Object> handleBindException(BindException ex, HttpHeaders headers, HttpStatus status,
			WebRequest request) {

		return handleValidationInternal(ex, headers, status, request, ex.getBindingResult());
	}

	private Problem.ProblemBuilder createProblemBuilder(HttpStatus status, ProblemType problemType, String detail) {
		return Problem.builder().timestamp(OffsetDateTime.now()).status(status.value()).type(problemType.getUri())
				.title(problemType.getTitle()).detail(detail);
	}

	private ResponseEntity<Object> handleValidationInternal(Exception ex, HttpHeaders headers, HttpStatus status,
			WebRequest request, BindingResult bindingResult) {
		var problemType = ProblemType.INVALID_DATA;
		var detail = "Um ou mais campos estão inválidos. Faça o preenchimento correto e tente novamente.";

		var problemObjects = bindingResult.getAllErrors().stream().map(objectError -> {
			var message = messageSource.getMessage(objectError, LocaleContextHolder.getLocale());
			var name = objectError.getObjectName();

			if (objectError instanceof FieldError) {
				name = ((FieldError) objectError).getField();
			}

			return ProblemObject.builder().name(name).userMessage(message).build();
		}).collect(Collectors.toList());

		var problem = createProblemBuilder(status, problemType, detail).userMessage(detail).objects(problemObjects)
				.build();

		return handleExceptionInternal(ex, problem, headers, status, request);
	}

	private ResponseEntity<Object> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		var problemType = ProblemType.INVALID_PARAMETER;
		var detail = String.format(
				"O parâmetro da URL '%s', recebeu o valor '%s', que é de um tipo inválido. Corrija e informe um valor compatível com o tipo '%s'",
				ex.getName(), ex.getValue(), ex.getRequiredType());

		var problem = createProblemBuilder(status, problemType, detail).userMessage(problemType.getTitle()).build();

		return handleExceptionInternal(ex, problem, headers, status, request);
	}

	private String joinPath(List<Reference> references) {
		return references.stream().map(Reference::getFieldName).collect(Collectors.joining("."));
	}

	private ResponseEntity<Object> handleInvalidFormat(InvalidFormatException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		var path = joinPath(ex.getPath());
		var problemType = ProblemType.INCOMPREHENSIBLE_MESSAGE;
		var detail = String.format(
				"A propriedade '%s' recebeu o valor '%s', que é de um tipo inválido. Corrija e informe um valor compatível com o tipo '%s'",
				path, ex.getValue(), ex.getTargetType().getSimpleName());
		var problem = createProblemBuilder(status, problemType, detail).userMessage(END_USER_GENERIC_MESSAGE).build();

		return handleExceptionInternal(ex, problem, headers, status, request);
	}

	private ResponseEntity<Object> handlePropertyBinding(PropertyBindingException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		var path = joinPath(ex.getPath());
		var problemType = ProblemType.INCOMPREHENSIBLE_MESSAGE;
		var detail = String
				.format("A propriedade '%s' não existe. Corrija ou remova essa propriedade e tente novamente.", path);
		var problem = createProblemBuilder(status, problemType, detail).userMessage(END_USER_GENERIC_MESSAGE).build();

		return handleExceptionInternal(ex, problem, headers, status, request);
	}

	private ResponseEntity<Object> handleMissing(MissingServletRequestParameterException ex, HttpHeaders headers,
			WebRequest request) {
		var status = HttpStatus.BAD_REQUEST;
		var problemType = ProblemType.INVALID_DATA;
		var detail = "Um ou mais parâmetros obrigatórios não estão presentes";
		var problemObject = ProblemObject.builder().name(ex.getParameterName())
				.userMessage(String.format("Parâmetro \"%s\" é obrigatório.", ex.getParameterName())).build();
		var problemObjects = new ArrayList<ProblemObject>();
		problemObjects.add(problemObject);
		var problem = createProblemBuilder(status, problemType, detail).userMessage(detail).objects(problemObjects)
				.build();

		return super.handleExceptionInternal(ex, problem, headers, status, request);
	}

	private ResponseEntity<Object> handleConstraint(ConstraintViolationException ex, HttpHeaders headers,
			WebRequest request) {
		var status = HttpStatus.BAD_REQUEST;
		var problemType = ProblemType.INVALID_DATA;
		var detail = "Um ou mais campos estão inválidos. Faça o preenchimento correto e tente novamente.";

		var problemObjects = ex.getConstraintViolations().stream().map(objectError -> {
			var name = objectError.getPropertyPath().toString();
			var message = objectError.getMessage();

			if (StringUtils.contains(name, ".")) {
				var pathNames = StringUtils.split(name, ".");
				name = pathNames[pathNames.length - 1];
			}

			if (objectError instanceof FieldError) {
				name = ((FieldError) objectError).getField();
			}

			return ProblemObject.builder().name(name).userMessage(message).build();
		}).collect(Collectors.toList());

		var problem = createProblemBuilder(status, problemType, detail).userMessage(detail).objects(problemObjects)
				.build();

		return super.handleExceptionInternal(ex, problem, headers, status, request);
	}

	private ResponseEntity<Object> handleNotFoundException(Exception ex, WebRequest request) {
		var status = HttpStatus.NOT_FOUND;
		var problemType = ProblemType.RESOURCE_NOT_FOUND;
		var detail = ex.getMessage();
		var problem = createProblemBuilder(status, problemType, detail).userMessage(detail).build();

		return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
	}
}
