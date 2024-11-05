package com.picpaydesafio.demopicpaydesafio.domain.transction.factory;

import com.picpaydesafio.demopicpaydesafio.domain.transction.model.Transaction;
import com.picpaydesafio.demopicpaydesafio.web.transaction.dtos.TransactionRequestDTO;

public interface TransactionFactory {

  Transaction createDomain (TransactionRequestDTO transactionRequest);

}
