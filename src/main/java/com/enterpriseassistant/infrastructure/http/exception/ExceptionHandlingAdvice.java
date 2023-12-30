package com.enterpriseassistant.infrastructure.http.exception;

import com.enterpriseassistant.client.exception.*;
import com.enterpriseassistant.invoice.exception.InvoiceAlreadyExists;
import com.enterpriseassistant.invoice.exception.InvoiceNotFound;
import com.enterpriseassistant.order.exception.InvalidOrderCreation;
import com.enterpriseassistant.order.exception.InvalidStatusUpdate;
import com.enterpriseassistant.order.exception.OrderNotFound;
import com.enterpriseassistant.product.exception.InvalidGtinNumberFormat;
import com.enterpriseassistant.product.exception.InvalidNetPrice;
import com.enterpriseassistant.product.exception.ProductNotFound;
import com.enterpriseassistant.product.exception.TakenGtin;
import com.enterpriseassistant.service.exception.ServiceAlreadyExists;
import com.enterpriseassistant.service.exception.ServiceNotFound;
import com.enterpriseassistant.user.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ExceptionHandlingAdvice {

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler({BadCredentialsException.class, IncorrectPassword.class})
    @ResponseBody
    public ErrorResponse handleBadCredentials() {
        return new ErrorResponse("Bad Credentials", HttpStatus.UNAUTHORIZED);
    }

    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    @ExceptionHandler(UserNotEnabled.class)
    @ResponseBody
    public ErrorResponse handleNotEnabled() {
        return new ErrorResponse("Not enabled", HttpStatus.METHOD_NOT_ALLOWED);
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler({UserNotFound.class, ServiceNotFound.class,
            ProductNotFound.class, OrderNotFound.class,
            InvoiceNotFound.class, AddressNotFound.class,
            ClientNotFound.class, RepresentativeNotFound.class})
    @ResponseBody
    public ErrorResponse handleNotFound() {
        return new ErrorResponse("Not found", HttpStatus.NOT_FOUND);
    }

    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    @ExceptionHandler({InvalidEmailFormat.class, InvalidUserId.class,
            InvalidUpdate.class, InvalidUserId.class,
            InvalidGtinNumberFormat.class, InvalidNetPrice.class,
            InvalidOrderCreation.class, InvalidStatusUpdate.class,
            InvalidPostalFormat.class, InvalidTaxIdFormat.class})
    @ResponseBody
    public ErrorResponse handleInvalidData() {
        return new ErrorResponse("Not acceptable request details", HttpStatus.NOT_ACCEPTABLE);
    }

    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    @ExceptionHandler({TakenEmail.class, TakenUsername.class,
            ServiceAlreadyExists.class, TakenGtin.class,
            InvoiceAlreadyExists.class, ExistingTaxIdNumber.class})
    @ResponseBody
    public ErrorResponse handleAlreadyExists() {
        return new ErrorResponse("Already Exists", HttpStatus.NOT_ACCEPTABLE);
    }

}
