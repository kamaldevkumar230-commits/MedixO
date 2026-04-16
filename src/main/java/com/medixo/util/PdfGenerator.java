package com.medixo.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.List;

import org.springframework.core.io.ClassPathResource;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;

import com.medixo.entity.Report;

public class PdfGenerator {

public static ByteArrayInputStream generate(
Report report,
List<String> medicines,
List<String> dosages,
List<String> durations){

Document document = new Document(PageSize.A4);
ByteArrayOutputStream out = new ByteArrayOutputStream();

try{

PdfWriter.getInstance(document,out);

document.open();

/* -------- LOGO -------- */

ClassPathResource imgFile =
new ClassPathResource("static/images/medixo_logo.png");

Image logo = Image.getInstance(imgFile.getURL());

logo.scaleToFit(80,80);
logo.setAlignment(Element.ALIGN_CENTER);

document.add(logo);

/* -------- HOSPITAL NAME -------- */

Font titleFont =
FontFactory.getFont(FontFactory.HELVETICA_BOLD,20);

Paragraph hospital =
new Paragraph("MEDIXO HOSPITAL",titleFont);

hospital.setAlignment(Element.ALIGN_CENTER);

document.add(hospital);

Paragraph sub =
new Paragraph("Advanced Digital Healthcare System");

sub.setAlignment(Element.ALIGN_CENTER);

document.add(sub);

document.add(new Paragraph(" "));

/* -------- PATIENT INFO -------- */

PdfPTable infoTable = new PdfPTable(2);
infoTable.setWidthPercentage(100);

infoTable.addCell("Patient Name");
infoTable.addCell(report.getPatientName());

infoTable.addCell("Doctor Name");
infoTable.addCell(report.getDoctorName());

infoTable.addCell("Diagnosis");
infoTable.addCell(report.getDisease());

infoTable.addCell("Date");
infoTable.addCell(report.getDate());

document.add(infoTable);

document.add(new Paragraph(" "));

/* -------- PRESCRIPTION TABLE -------- */

Font headerFont =
FontFactory.getFont(FontFactory.HELVETICA_BOLD,12);

Paragraph p =
new Paragraph("PRESCRIPTION",headerFont);

document.add(p);

document.add(new Paragraph(" "));

PdfPTable table = new PdfPTable(3);

table.setWidthPercentage(100);



table.addCell("MEDICINE");
table.addCell("DOSAGE");
table.addCell("DURATION");

int size = Math.min(medicines.size(),
            Math.min(dosages.size(), durations.size()));

for(int i=0;i<size;i++){

table.addCell(medicines.get(i).trim());
table.addCell(dosages.get(i).trim());
table.addCell(durations.get(i).trim());


}

document.add(table);

document.add(new Paragraph(" "));
document.add(new Paragraph("Doctor Notes :"));
document.add(new Paragraph(report.getNotes()));

document.add(new Paragraph(" "));
document.add(new Paragraph(" "));
document.add(new Paragraph("Doctor Signature : "+report.getDoctorName()));

document.close();

}catch(Exception e){

e.printStackTrace();

}

return new ByteArrayInputStream(out.toByteArray());

}

public static ByteArrayInputStream generate(Report report){

List<String> medicines = List.of(report.getMedicine());
List<String> dosages = List.of(report.getDosage());
List<String> durations = List.of(report.getDuration());

return generate(report, medicines, dosages, durations);

}

}