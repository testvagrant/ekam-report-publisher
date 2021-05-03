package com.testvagrant.optimus.dashboard.listeners;

import com.testvagrant.optimus.dashboard.publishers.OptimusReportPublisher;
import org.testng.IReporter;
import org.testng.ISuite;
import org.testng.xml.XmlSuite;

import java.util.List;

public class ReportListener implements IReporter {
    @Override
    public void generateReport(List<XmlSuite> xmlSuites, List<ISuite> suites, String outputDirectory) {
        OptimusReportPublisher optimusReportPublisher = new OptimusReportPublisher("http://localhost:8090/");
        optimusReportPublisher.publish();
    }
}