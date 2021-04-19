package com.testvagrant.optimus.dashboard.listeners;

import com.testvagrant.optimus.dashboard.publishers.OptimusTestNGReportPublisher;
import org.testng.IReporter;
import org.testng.ISuite;
import org.testng.xml.XmlSuite;

import java.util.List;

public class ReportListener implements IReporter {
    @Override
    public void generateReport(List<XmlSuite> xmlSuites, List<ISuite> suites, String outputDirectory) {
        OptimusTestNGReportPublisher optimusTestNGReportPublisher = new OptimusTestNGReportPublisher("http://35.240.199.88/",suites);
        optimusTestNGReportPublisher.publish();
    }
}