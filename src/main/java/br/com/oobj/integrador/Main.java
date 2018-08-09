package br.com.oobj.integrador;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import br.com.oobj.integrador.dao.NotaFiscalDAO;
import br.com.oobj.integrador.destino.Destino;

public class Main {
	
	public static void main(String[] args) {
		ApplicationContext contextoDoSpring = 
				new ClassPathXmlApplicationContext("applicationContext.xml");
		
		
			NotaFiscalDAO notaFiscalDao = contextoDoSpring.getBean(NotaFiscalDAO.class);		
		
		
		
				
//		NotaFiscalDAO notaFiscalDAO = contextoDoSpring.getBean("notaFiscalDao", NotaFiscalDAO.class);
//		
//		System.out.println("Quantidade de registros: " + notaFiscalDAO.contar());
//		
//		NotaFiscal notaFiscal = new NotaFiscal();
//		notaFiscal.setNomeArquivo("nova nota");
//		notaFiscal.setConteudoArquivo("novo conteudo");
//		// import java.util.Date;
//		// 'new Date()' retorna a hora local da maquina, no momento da execucao...
//		notaFiscal.setDataHoraEmissao(new Date());
//		notaFiscalDAO.inserirNotaFiscal(notaFiscal);
//		
//		System.out.println("Quantidade de registros: " + notaFiscalDAO.contar());
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
//		NotaFiscal notaFiscal = new NotaFiscal();
//		notaFiscal.setId(2L);
//		// nome: procNFE35130303555225000445550230002594841319344452AUT.xml
//		notaFiscal.setNomeArquivo("novo nome.xml");
//		notaFiscal.setConteudoArquivo("novo conteudo");
//		
//		notaFiscalDAO.atualizar(notaFiscal);
//		System.out.println("Nota atualizada: " + notaFiscal);
		
		
		
		
		
		
		
		
		
		
		
		
		
		
//		Origem origemBean = contextoDoSpring.getBean("origem", Origem.class);
//		Destino destinoBean = contextoDoSpring.getBean("destino", Destino.class);
//		
//		IntegradorNotaFiscal integrador = new IntegradorNotaFiscal();
//		integrador.setOrigem(origemBean);
//		integrador.setDestino(destinoBean);
//		
//		while (true) {
//			integrador.integraNotaFiscal();
//			
//			try {
//				Thread.sleep(5000);
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			}
//		}
	}

}
