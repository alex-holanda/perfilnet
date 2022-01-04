package br.com.perfilnet.bankslip.infrastructure.service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;

import br.com.perfilnet.bankslip.domain.service.BankSlipJoinService;

@Service
public class BankSlipJoinServiceImpl implements BankSlipJoinService {

	@Override
	public byte[] join(List<MultipartFile> files) {

		var outputFiles = new ByteArrayOutputStream();
		var destinationDocument = new PdfDocument(new PdfWriter(outputFiles));

		files.stream().forEach(file -> {
			try {
				var source = new PdfDocument(new PdfReader(new ByteArrayInputStream(file.getBytes())));

				for (int i = 1; i <= source.getNumberOfPages(); i++) {
					destinationDocument.addPage(source.getPage(i).copyTo(destinationDocument));
				}

				var splitDocument = new PdfDocument(
						new PdfReader(this.getClass().getResourceAsStream("/assets/separacao.pdf")));

				for (int x = 1; x <= splitDocument.getNumberOfPages(); x++) {
					destinationDocument.addPage(splitDocument.getPage(x).copyTo(destinationDocument));
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		});

		destinationDocument.close();

		var pagedDocument = new ByteArrayOutputStream();

		try {
			var pagedDestinationDocument = new PdfDocument(
					new PdfReader(new ByteArrayInputStream(outputFiles.toByteArray())), new PdfWriter(pagedDocument));

			for (int i = 1; i < pagedDestinationDocument.getNumberOfPages(); i++) {
				var page = pagedDestinationDocument.getPage(i);
				var canvas = new PdfCanvas(page);

				if (i % 2 == 0) {
					canvas.beginText().setFontAndSize(PdfFontFactory.createFont(StandardFonts.HELVETICA), 10)
							.moveText(page.getPageSize().getWidth() / 2 - 10, 10).showText(String.valueOf(i - 1))
							.showText(" - ").showText(String.valueOf(i)).endText();

					canvas.beginText().setFontAndSize(PdfFontFactory.createFont(StandardFonts.HELVETICA), 10)
							.moveText(page.getPageSize().getWidth() / 2, page.getPageSize().getHeight() - 15)
							.showText(String.valueOf(i - 1)).showText(" - ").showText(String.valueOf(i)).endText();
				}
			}

			pagedDestinationDocument.close();

		} catch (IOException e) {
			e.getStackTrace();
		}

		return pagedDocument.toByteArray();
	}

}
