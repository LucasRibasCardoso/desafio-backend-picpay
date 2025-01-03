package com.picpaydesafio.demopicpaydesafio.domain.factories;

import com.picpaydesafio.demopicpaydesafio.domain.models.Transaction;
import com.picpaydesafio.demopicpaydesafio.web.dtos.TransactionRequestDTO;

public interface TransactionFactory {

  Transaction createDomain (TransactionRequestDTO transactionRequest);

}
