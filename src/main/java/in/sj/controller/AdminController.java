package in.sj.controller;

import java.security.Principal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.lowagie.text.Document;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

import in.sj.entity.Order;
import in.sj.service.AdminDashboardService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private static final Logger log =
            LoggerFactory.getLogger(AdminController.class);

    private final AdminDashboardService dashboardService;

    // ================= DASHBOARD =================
    // http://localhost:8080/admin/dashboard
    @GetMapping("/dashboard")
    public String dashboard(Model model, Principal principal) {

        if (principal != null) {
            log.info("ADMIN DASHBOARD ACCESS | admin={}", principal.getName());
        } else {
            log.warn("ADMIN DASHBOARD ACCESS | anonymous user");
        }

        // ===== BASIC STATS =====
        model.addAttribute("totalProducts", dashboardService.getTotalProducts());
        model.addAttribute("totalUsers", dashboardService.getTotalUsers());
        model.addAttribute("totalOrders", dashboardService.getTotalOrders());
        model.addAttribute("totalRevenue", dashboardService.getTotalRevenue());

     // ===== MONTHLY SALES FOR CHART (LAST 6 MONTHS) =====
        List<Object[]> result = dashboardService.getMonthlySales();

        // Map: month -> total
        Map<Integer, Double> salesMap = new HashMap<>();
        for (Object[] row : result) {
            Integer month = ((Number) row[0]).intValue();   // 1..12
            Double total = ((Number) row[1]).doubleValue();
            salesMap.put(month, total);
        }

        // Prepare last 6 months
        List<String> months = new ArrayList<>();
        List<Double> monthlySales = new ArrayList<>();

        LocalDate now = LocalDate.now();

        for (int i = 5; i >= 0; i--) {
            LocalDate m = now.minusMonths(i);
            int monthNumber = m.getMonthValue(); // 1..12

            months.add(m.getMonth().name()); // e.g. FEBRUARY
            monthlySales.add(salesMap.getOrDefault(monthNumber, 0.0));
        }

        model.addAttribute("months", months);
        model.addAttribute("monthlySales", monthlySales);


        return "admin-dashboard";
    }

    // ================= EXPORT EXCEL =================
    // http://localhost:8080/admin/report/excel
    @GetMapping("/report/excel")
    public void exportExcel(HttpServletResponse response) throws Exception {

        log.info("EXPORT EXCEL REPORT");

        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=sales-report.xlsx");

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Sales Report");

        Row header = sheet.createRow(0);
        header.createCell(0).setCellValue("Order ID");
        header.createCell(1).setCellValue("Customer");
        header.createCell(2).setCellValue("Amount");
        header.createCell(3).setCellValue("Date");

        List<Order> orders = dashboardService.getAllOrders();

        int rowNum = 1;
        for (Order o : orders) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(o.getId());
            row.createCell(1).setCellValue(o.getUsername());
            row.createCell(2).setCellValue(o.getTotalAmount());
            row.createCell(3).setCellValue(o.getOrderDate().toString());
        }

        workbook.write(response.getOutputStream());
        workbook.close();
    }

    // ================= EXPORT PDF =================
    // http://localhost:8080/admin/report/pdf
    @GetMapping("/report/pdf")
    public void exportPdf(HttpServletResponse response) throws Exception {

        log.info("EXPORT PDF REPORT");

        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=sales-report.pdf");

        Document document = new Document();
        PdfWriter.getInstance(document, response.getOutputStream());

        document.open();
        document.add(new Paragraph("Sales Report"));
        document.add(new Paragraph(" "));

        PdfPTable table = new PdfPTable(4);
        table.addCell("Order ID");
        table.addCell("Customer");
        table.addCell("Amount");
        table.addCell("Date");

        List<Order> orders = dashboardService.getAllOrders();

        for (Order o : orders) {
            table.addCell(String.valueOf(o.getId()));
            table.addCell(o.getUsername());
            table.addCell(String.valueOf(o.getTotalAmount()));
            table.addCell(o.getOrderDate().toString());
        }

        document.add(table);
        document.close();
    }
}
