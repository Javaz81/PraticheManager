/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.javazsoft81.pratichemanager.tests;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.junit.Test;

/**
 *
 * @author iavazzo.andrea
 */
public class StampaPratica {
    
    public StampaPratica() {
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
    @Test
    public void stampa() throws IOException, InvalidFormatException{
        Files.list(Paths.get("src/main/resources/TEMPLATES")).forEach(i->{
            System.out.println("PATH:".concat(i.getFileName().toString()));
        });
//        XWPFDocument doc = new XWPFDocument(OPCPackage.open("C:\\LAVORI.docx"));
//        for (XWPFParagraph p : doc.getParagraphs()) {
//            List<XWPFRun> runs = p.getRuns();
//            if (runs != null) {
//                for (XWPFRun r : runs) {
//                    String text = r.getText(0);
//                    if (text != null && text.contains("[TITOLO]")) {
//                        text = text.replace("[TITOLO]","SUPERASSISTENZA");
//                        r.setText("CAZZO");
//                        r.addBreak();
//                        r.setText("CAZZO 2 !!!");
//                        r.setText(text, 0);
//                    }
//                }
//            }
//        }
//        doc.write(new FileOutputStream("output.docx"));
    }
}
