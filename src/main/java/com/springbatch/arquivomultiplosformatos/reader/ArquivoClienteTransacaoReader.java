package com.springbatch.arquivomultiplosformatos.reader;

import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemStreamException;
import org.springframework.batch.item.ItemStreamReader;

import com.springbatch.arquivomultiplosformatos.dominio.Cliente;
import com.springbatch.arquivomultiplosformatos.dominio.Transacao;

public class ArquivoClienteTransacaoReader implements ItemStreamReader<Cliente> {
	
	private Object objetoAtual;
	private ItemStreamReader<Object> delegate;
	
	public ArquivoClienteTransacaoReader(ItemStreamReader<Object> delegate) {
		this.delegate = delegate;
	}

	@Override
	public void open(ExecutionContext executionContext) throws ItemStreamException {
		delegate.open(executionContext);
		
	}

	@Override
	public void update(ExecutionContext executionContext) throws ItemStreamException {
		delegate.update(executionContext);
		
	}

	@Override
	public void close() throws ItemStreamException {
		delegate.close();
		
	}

	@Override
	public Cliente read() throws Exception {
		if (objetoAtual == null)
			objetoAtual = delegate.read();
			
		Cliente cliente = (Cliente) objetoAtual;
		objetoAtual = null;
		
		if (cliente != null) {
			while(peek() instanceof Transacao)
				cliente.getTransacoes().add((Transacao) objetoAtual);
		}
		
		return cliente;
		
	}

	private Object peek() throws Exception {
		objetoAtual = delegate.read();
		return objetoAtual;
	}

}
