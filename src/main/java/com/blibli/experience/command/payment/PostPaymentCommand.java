package com.blibli.experience.command.payment;

import com.blibli.experience.model.request.payment.PostPaymentRequest;
import com.blibli.experience.model.response.payment.PostPaymentResponse;
import com.blibli.oss.command.Command;

public interface PostPaymentCommand extends Command<PostPaymentRequest, PostPaymentResponse> {
}
