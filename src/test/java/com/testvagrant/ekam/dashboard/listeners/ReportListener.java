package com.testvagrant.ekam.dashboard.listeners;

import com.testvagrant.ekam.dashboard.publishers.EkamReportPublisher;
import org.testng.IReporter;
import org.testng.ISuite;
import org.testng.xml.XmlSuite;

import java.util.List;

public class ReportListener implements IReporter {
    @Override
    public void generateReport(List<XmlSuite> xmlSuites, List<ISuite> suites, String outputDirectory) {
        EkamReportPublisher optimusReportPublisher = new EkamReportPublisher("http://localhost:8090/");
        optimusReportPublisher.publish();
    }
}