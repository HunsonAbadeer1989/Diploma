package main.exception;

import lombok.Data;
import main.api.response.ResponseApi;

@Data
public class NotFoundOrBadRequestResponse extends RuntimeException implements ResponseApi {

}
