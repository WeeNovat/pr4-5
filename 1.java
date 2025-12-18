import java.util.*;
import java.time.LocalDate;

// ==================== 1. ІНТЕРФЕЙС ДЛЯ КЛЮЧОВОЇ ПОВЕДІНКИ ====================
interface DocumentOperations {
    // Абстрактний метод
    void generateContent();
    
    // Метод за замовчуванням
    default String getDocumentType() {
        return "Загальний документ";
    }
    
    // Ще один метод за замовчуванням
    default boolean isValidForArchiving() {
        return true;
    }
    
    // Статичний метод
    static String getDocumentStandard() {
        return "ISO 9001:2015";
    }
}

// ==================== 2. АБСТРАКТНИЙ БАЗОВИЙ КЛАС ====================
abstract class Document implements DocumentOperations {
    protected String title;
    protected String author;
    protected LocalDate creationDate;
    protected String documentId;
    protected int pageCount;
    
    // Конструктор базового класу
    public Document(String title, String author, String documentId, int pageCount) {
        this.title = title;
        this.author = author;
        this.documentId = documentId;
        this.pageCount = pageCount;
        this.creationDate = LocalDate.now();
    }
    
    // Абстрактні методи
    public abstract double calculatePrintingCost();
    public abstract String getFormat();
    
    // Конкретні методи базового класу
    public String getDocumentInfo() {
        return String.format("ID: %s | Назва: %s | Автор: %s | Дата: %s | Сторінок: %d",
                documentId, title, author, creationDate, pageCount);
    }
    
    public void archive() {
        System.out.println("Архівація документа: " + title);
    }
    
    // Перевизначення методу з інтерфейсу
    @Override
    public void generateContent() {
        System.out.println("Генерується зміст документа: " + title);
    }
    
    // Getter/Setter
    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public LocalDate getCreationDate() { return creationDate; }
    public String getDocumentId() { return documentId; }
    public int getPageCount() { return pageCount; }
    public void setPageCount(int pageCount) { this.pageCount = pageCount; }
}

// ==================== 3. КОНКРЕТНІ ПІДКЛАСИ ====================

// Підклас 1: Договір
class Contract extends Document {
    private String contractType; // трудовий, цивільний, комерційний
    private LocalDate validUntil;
    private double contractValue;
    
    public Contract(String title, String author, String documentId, int pageCount,
                    String contractType, LocalDate validUntil, double contractValue) {
        super(title, author, documentId, pageCount);
        this.contractType = contractType;
        this.validUntil = validUntil;
        this.contractValue = contractValue;
    }
    
    @Override
    public double calculatePrintingCost() {
        // Вартість друку договору: базова ціна + додаток за тип
        double baseCost = pageCount * 2.5;
        if (contractType.equals("трудовий")) {
            return baseCost + 50;
        } else if (contractType.equals("комерційний")) {
            return baseCost + (contractValue * 0.001);
        }
        return baseCost;
    }
    
    @Override
    public String getFormat() {
        return "Юридичний формат A4, двосторонній друк";
    }
    
    @Override
    public void generateContent() {
        super.generateContent();
        System.out.println("Додається юридична рамка та стандартні пункти договору");
    }
    
    // Перевизначення методу за замовчуванням з інтерфейсу
    @Override
    public String getDocumentType() {
        return "Договір (" + contractType + ")";
    }
    
    @Override
    public boolean isValidForArchiving() {
        return validUntil.isAfter(LocalDate.now());
    }
    
    // Специфічний метод для підкласу
    public boolean isExpired() {
        return LocalDate.now().isAfter(validUntil);
    }
    
    public String getContractType() { return contractType; }
    public LocalDate getValidUntil() { return validUntil; }
    public double getContractValue() { return contractValue; }
}

// Підклас 2: Звіт
class Report extends Document {
    private String reportPeriod; // щоденний, щотижневий, щомісячний
    private boolean hasCharts;
    private String department;
    
    public Report(String title, String author, String documentId, int pageCount,
                  String reportPeriod, boolean hasCharts, String department) {
        super(title, author, documentId, pageCount);
        this.reportPeriod = reportPeriod;
        this.hasCharts = hasCharts;
        this.department = department;
    }
    
    @Override
    public double calculatePrintingCost() {
        // Вартість друку звіту: базова ціна + додаток за графіки + кольоровий друк
        double cost = pageCount * 1.8;
        if (hasCharts) {
            cost += 30;
        }
        if (department.equals("маркетинг") || department.equals("дизайн")) {
            cost *= 1.5; // кольоровий друк
        }
        return cost;
    }
    
    @Override
    public String getFormat() {
        return "Корпоративний формат, з логотипом компанії";
    }
    
    @Override
    public void generateContent() {
        super.generateContent();
        System.out.println("Додаються таблиці, графіки та аналітичні висновки");
        if (hasCharts) {
            System.out.println("Генеруються графіки для періоду: " + reportPeriod);
        }
    }
    
    // Перевизначення методу за замовчуванням
    @Override
    public String getDocumentType() {
        return "Звіт (" + reportPeriod + ") від " + department;
    }
    
    // Специфічний метод для підкласу
    public void addExecutiveSummary() {
        System.out.println("Додається виконавче резюме для звіту: " + title);
    }
    
    public String getReportPeriod() { return reportPeriod; }
    public boolean hasCharts() { return hasCharts; }
    public String getDepartment() { return department; }
}

// Підклас 3: Презентація
class Presentation extends Document {
    private String presentationTheme;
    private int slideCount;
    private boolean hasAnimations;
    
    public Presentation(String title, String author, String documentId, int pageCount,
                        String presentationTheme, int slideCount, boolean hasAnimations) {
        super(title, author, documentId, pageCount);
        this.presentationTheme = presentationTheme;
        this.slideCount = slideCount;
        this.hasAnimations = hasAnimations;
    }
    
    @Override
    public double calculatePrintingCost() {
        // Для презентації: ціна за слайд + додаток за анімації
        double cost = slideCount * 3.0;
        if (hasAnimations) {
            cost += 25; // додаткове форматування
        }
        return cost;
    }
    
    @Override
    public String getFormat() {
        return "Презентаційний формат 16:9, кольоровий друк";
    }
    
    @Override
    public void generateContent() {
        super.generateContent();
        System.out.println("Створюється структура слайдів для теми: " + presentationTheme);
        System.out.println("Кількість слайдів: " + slideCount);
        if (hasAnimations) {
            System.out.println("Додаються анімації та переходи");
        }
    }
    
    // Перевизначення методу за замовчуванням
    @Override
    public boolean isValidForArchiving() {
        // Презентації застарівають швидше
        return creationDate.isAfter(LocalDate.now().minusMonths(6));
    }
    
    // Специфічний метод для підкласу
    public void startSlideShow() {
        System.out.println("Запуск слайд-шоу презентації: " + title);
        System.out.println("Тема: " + presentationTheme);
        System.out.println("Кількість слайдів: " + slideCount);
    }
    
    public String getPresentationTheme() { return presentationTheme; }
    public int getSlideCount() { return slideCount; }
    public boolean hasAnimations() { return hasAnimations; }
}

// ==================== 4. ФАБРИКА ====================
class DocumentFactory {
    
    // Фабричний метод
    public static Document createDocument(String docType, Map<String, Object> params) {
        String title = (String) params.getOrDefault("title", "Без назви");
        String author = (String) params.getOrDefault("author", "Невідомий автор");
        String docId = generateDocumentId(docType);
        int pageCount = (int) params.getOrDefault("pageCount", 1);
        
        switch (docType.toLowerCase()) {
            case "contract":
                String contractType = (String) params.getOrDefault("contractType", "цивільний");
                LocalDate validUntil = (LocalDate) params.getOrDefault("validUntil", 
                    LocalDate.now().plusYears(1));
                double contractValue = (double) params.getOrDefault("contractValue", 0.0);
                return new Contract(title, author, docId, pageCount, 
                    contractType, validUntil, contractValue);
                    
            case "report":
                String reportPeriod = (String) params.getOrDefault("reportPeriod", "щомісячний");
                boolean hasCharts = (boolean) params.getOrDefault("hasCharts", false);
                String department = (String) params.getOrDefault("department", "загальний");
                return new Report(title, author, docId, pageCount,
                    reportPeriod, hasCharts, department);
                    
            case "presentation":
                String theme = (String) params.getOrDefault("theme", "Загальна тема");
                int slides = (int) params.getOrDefault("slides", 10);
                boolean hasAnimations = (boolean) params.getOrDefault("hasAnimations", false);
                return new Presentation(title, author, docId, pageCount,
                    theme, slides, hasAnimations);
                    
            default:
                throw new IllegalArgumentException("Невідомий тип документа: " + docType);
        }
    }
    
    // Перевантажений фабричний метод для швидкого створення
    public static Document createSimpleContract(String title, String author) {
        Map<String, Object> params = new HashMap<>();
        params.put("title", title);
        params.put("author", author);
        params.put("contractType", "цивільний");
        params.put("contractValue", 1000.0);
        return createDocument("contract", params);
    }
    
    public static Document createSimpleReport(String title, String author, String department) {
        Map<String, Object> params = new HashMap<>();
        params.put("title", title);
        params.put("author", author);
        params.put("department", department);
        params.put("hasCharts", true);
        return createDocument("report", params);
    }
    
    private static String generateDocumentId(String docType) {
        String prefix = "";
        switch (docType.toLowerCase()) {
            case "contract": prefix = "CONT"; break;
            case "report": prefix = "REP"; break;
            case "presentation": prefix = "PRES"; break;
        }
        return prefix + "-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }
}

// ==================== 5. ДЕМОНСТРАЦІЙНИЙ КЛАС ====================
class DocumentSystemDemo {
    public static void main(String[] args) {
        System.out.println("=== СИСТЕМА КЕРУВАННЯ ДОКУМЕНТАМИ ===\n");
        
        // 1. Демонстрація фабрики
        System.out.println("1. ДЕМОНСТРАЦІЯ ФАБРИКИ:");
        Document contract = DocumentFactory.createSimpleContract(
            "Договір про надання послуг", "Іван Петренко");
        System.out.println("Створено: " + contract.getDocumentInfo());
        
        Document report = DocumentFactory.createSimpleReport(
            "Фінансовий звіт за І квартал", "Марія Сидоренко", "фінанси");
        System.out.println("Створено: " + report.getDocumentInfo());
        
        // 2. Демонстрація поліморфізму
        System.out.println("\n2. ДЕМОНСТРАЦІЯ ПОЛІМОРФІЗМУ:");
        List<Document> documents = new ArrayList<>();
        
        // Створення різних документів через фабрику
        Map<String, Object> params = new HashMap<>();
        params.put("title", "Трудовий договір");
        params.put("author", "HR відділ");
        params.put("contractType", "трудовий");
        params.put("contractValue", 50000.0);
        params.put("validUntil", LocalDate.now().plusYears(3));
        params.put("pageCount", 5);
        
        documents.add(DocumentFactory.createDocument("contract", params));
        
        params.clear();
        params.put("title", "Щотижневий звіт про продажі");
        params.put("author", "Відділ продажів");
        params.put("reportPeriod", "щотижневий");
        params.put("hasCharts", true);
        params.put("department", "продажі");
        params.put("pageCount", 8);
        
        documents.add(DocumentFactory.createDocument("report", params));
        
        params.clear();
        params.put("title", "Презентація нового продукту");
        params.put("author", "Відділ маркетингу");
        params.put("theme", "Запуск нового продукту");
        params.put("slides", 15);
        params.put("hasAnimations", true);
        params.put("pageCount", 15);
        
        documents.add(DocumentFactory.createDocument("presentation", params));
        
        // 3. Обробка колекції базового типу
        System.out.println("\n3. ОБРОБКА КОЛЕКЦІЇ БАЗОВОГО ТИПУ:");
        System.out.println("Загальна інформація про всі документи:");
        for (Document doc : documents) {
            System.out.println("- " + doc.getDocumentInfo());
            System.out.println("  Тип: " + doc.getDocumentType());
            System.out.println("  Формат: " + doc.getFormat());
            System.out.println("  Вартість друку: " + doc.calculatePrintingCost() + " грн");
            System.out.println("  Придатний для архіву: " + doc.isValidForArchiving());
            System.out.println();
        }
        
        // 4. Виклик перевизначених методів
        System.out.println("4. ВИКОНАННЯ СПЕЦИФІЧНИХ ОПЕРАЦІЙ:");
        for (Document doc : documents) {
            doc.generateContent();
            doc.archive();
            System.out.println();
        }
        
        // 5. Виклик методів за замовчуванням з інтерфейсу
        System.out.println("5. МЕТОДИ ЗА ЗАМОВЧУВАННЯМ З ІНТЕРФЕЙСУ:");
        for (Document doc : documents) {
            System.out.println("Документ: " + doc.getTitle());
            System.out.println("  Тип (з інтерфейсу): " + doc.getDocumentType());
            System.out.println("  Придатний для архіву (з інтерфейсу): " + doc.isValidForArchiving());
            System.out.println();
        }
        
        // 6. Демонстрація специфічних методів підкласів
        System.out.println("6. СПЕЦИФІЧНІ МЕТОДИ ПІДКЛАСІВ:");
        for (Document doc : documents) {
            if (doc instanceof Contract) {
                Contract c = (Contract) doc;
                System.out.println("Договір: " + c.getTitle());
                System.out.println("  Тип договору: " + c.getContractType());
                System.out.println("  Дійсний до: " + c.getValidUntil());
                System.out.println("  Прострочений: " + c.isExpired());
            } else if (doc instanceof Report) {
                Report r = (Report) doc;
                System.out.println("Звіт: " + r.getTitle());
                System.out.println("  Відділ: " + r.getDepartment());
                System.out.println("  Період: " + r.getReportPeriod());
                r.addExecutiveSummary();
            } else if (doc instanceof Presentation) {
                Presentation p = (Presentation) doc;
                System.out.println("Презентація: " + p.getTitle());
                System.out.println("  Тема: " + p.getPresentationTheme());
                System.out.println("  Слайдів: " + p.getSlideCount());
                p.startSlideShow();
            }
            System.out.println();
        }
        
        // 7. Виклик статичного методу інтерфейсу
        System.out.println("7. СТАТИЧНИЙ МЕТОД ІНТЕРФЕЙСУ:");
        System.out.println("Стандарт документів: " + DocumentOperations.getDocumentStandard());
        
        System.out.println("\n=== ДЕМОНСТРАЦІЯ ЗАВЕРШЕНА ===");
    }
}

// ==================== 6. МОДУЛЬНІ ТЕСТИ ====================
class DocumentSystemTest {
    
    // Тест 1: Перевірка фабрики - створення різних типів документів
    public static void testDocumentFactory() {
        System.out.println("\n=== Тест 1: Фабрика документів ===");
        
        // Тест створення договору
        Map<String, Object> params = new HashMap<>();
        params.put("title", "Тестовий договір");
        params.put("author", "Тестер");
        params.put("contractType", "трудовий");
        params.put("contractValue", 10000.0);
        params.put("validUntil", LocalDate.now().plusYears(1));
        
        Document contract = DocumentFactory.createDocument("contract", params);
        assert contract != null : "Фабрика не створила договір";
        assert contract instanceof Contract : "Створений документ не є договором";
        assert contract.getTitle().equals("Тестовий договір") : "Невірна назва договору";
        System.out.println("✓ Тест фабрики договорів пройдено");
        
        // Тест створення звіту
        params.clear();
        params.put("title", "Тестовий звіт");
        params.put("author", "Тестер");
        params.put("department", "тестування");
        
        Document report = DocumentFactory.createDocument("report", params);
        assert report != null : "Фабрика не створила звіт";
        assert report instanceof Report : "Створений документ не є звітом";
        System.out.println("✓ Тест фабрики звітів пройдено");
        
        // Тест невірного типу документа
        try {
            DocumentFactory.createDocument("невідомий", params);
            assert false : "Очікувалася IllegalArgumentException";
        } catch (IllegalArgumentException e) {
            System.out.println("✓ Тест обробки невірного типу пройдено");
        }
    }
    
    // Тест 2: Перевірка поліморфізму - обробка колекції базового типу
    public static void testPolymorphism() {
        System.out.println("\n=== Тест 2: Поліморфізм ===");
        
        List<Document> docs = new ArrayList<>();
        docs.add(DocumentFactory.createSimpleContract("Договір 1", "Автор 1"));
        docs.add(DocumentFactory.createSimpleReport("Звіт 1", "Автор 2", "відділ 1"));
        
        // Перевірка, що всі об'єкти можна обробляти як Document
        for (Document doc : docs) {
            // Виклик методів базового класу
            String info = doc.getDocumentInfo();
            double cost = doc.calculatePrintingCost();
            String format = doc.getFormat();
            
            assert info != null && !info.isEmpty() : "Метод getDocumentInfo повернув порожнє значення";
            assert cost >= 0 : "Вартість друку не може бути від'ємною";
            assert format != null && !format.isEmpty() : "Метод getFormat повернув порожнє значення";
            
            // Виклик методу з інтерфейсу
            doc.generateContent();
        }
        System.out.println("✓ Тест поліморфізму пройдено");
    }
    
    // Тест 3: Перевірка перевизначення методу за замовчуванням
    public static void testDefaultMethodOverride() {
        System.out.println("\n=== Тест 3: Перевизначення методу за замовчуванням ===");
        
        Map<String, Object> params = new HashMap<>();
        params.put("title", "Тестовий договір");
        params.put("author", "Тест");
        params.put("contractType", "комерційний");
        params.put("validUntil", LocalDate.now().minusDays(1)); // Прострочений
        
        Contract contract = (Contract) DocumentFactory.createDocument("contract", params);
        
        // Перевірка перевизначеного методу getDocumentType()
        String docType = contract.getDocumentType();
        assert docType.contains("Договір") && docType.contains("комерційний") : 
            "Метод getDocumentType не перевизначено правильно";
        
        // Перевірка перевизначеного методу isValidForArchiving()
        boolean canArchive = contract.isValidForArchiving();
        assert !canArchive : "Прострочений договір не повинен бути придатним для архівування";
        
        System.out.println("✓ Тест перевизначення методів за замовчуванням пройдено");
    }
    
    // Тест 4: Перевірка специфічних методів підкласів
    public static void testSpecificMethods() {
        System.out.println("\n=== Тест 4: Специфічні методи підкласів ===");
        
        // Тест для Contract
        Map<String, Object> params = new HashMap<>();
        params.put("title", "Тест");
        params.put("author", "Тест");
        params.put("validUntil", LocalDate.now().minusDays(1));
        
        Contract contract = (Contract) DocumentFactory.createDocument("contract", params);
        assert contract.isExpired() : "Метод isExpired() повинен повертати true для простроченого договору";
        
        // Тест для Report
        Report report = (Report) DocumentFactory.createSimpleReport("Тест", "Автор", "тест");
        // Метод addExecutiveSummary() не повертає значення, але має виконуватись без помилок
        report.addExecutiveSummary();
        
        // Тест для Presentation
        params.clear();
        params.put("title", "Тест");
        params.put("author", "Тест");
        params.put("slides", 5);
        
        Presentation pres = (Presentation) DocumentFactory.createDocument("presentation", params);
        assert pres.getSlideCount() == 5 : "Метод getSlideCount() повертає невірне значення";
        
        System.out.println("✓ Тест специфічних методів пройдено");
    }

    // Тест 5: Граничні випадки
    public static void testEdgeCases() {
        System.out.println("\n=== Тест 5: Граничні випадки ===");
        
        // Тест з нульовою кількістю сторінок
        Map<String, Object> params = new HashMap<>();
        params.put("title", "Порожній документ");
        params.put("author", "Система");
        params.put("pageCount", 0);
        
        Document doc = DocumentFactory.createDocument("contract", params);
        double cost = doc.calculatePrintingCost();
        assert cost >= 0 : "Вартість друку при 0 сторінок не може бути від'ємною";
        
        // Тест з дуже великою кількістю сторінок
        params.put("pageCount", 1000);
        doc = DocumentFactory.createDocument("report", params);
        cost = doc.calculatePrintingCost();
        assert cost > 0 : "Вартість друку 1000 сторінок повинна бути більше 0";
        
        // Тест з порожніми значеннями
        params.clear();
        params.put("title", "");
        params.put("author", "");
        doc = DocumentFactory.createDocument("presentation", params);
        assert doc.getTitle().isEmpty() : "Назва має бути порожньою";
        
        System.out.println("✓ Тест граничних випадків пройдено");
    }
    
    // Тест 6: Перевірка обробки колекції з різними типами
    public static void testCollectionProcessing() {
        System.out.println("\n=== Тест 6: Обробка колекції ===");
        
        List<Document> mixedDocs = new ArrayList<>();
        
        // Додаємо різні типи документів
        mixedDocs.add(DocumentFactory.createSimpleContract("Договір", "Автор"));
        mixedDocs.add(DocumentFactory.createSimpleReport("Звіт", "Автор", "відділ"));
        
        // Підрахунок загальної вартості друку
        double totalCost = 0;
        int contractCount = 0;
        int reportCount = 0;
        
        for (Document doc : mixedDocs) {
            totalCost += doc.calculatePrintingCost();
            
            if (doc instanceof Contract) contractCount++;
            if (doc instanceof Report) reportCount++;
            
            // Перевірка, що всі документи реалізують інтерфейс
            assert doc instanceof DocumentOperations : 
                "Всі документи повинні реалізовувати DocumentOperations";
        }
        
        assert totalCost > 0 : "Загальна вартість повинна бути більше 0";
        assert contractCount == 1 : "Має бути рівно 1 договір";
        assert reportCount == 1 : "Має бути рівно 1 звіт";
        
        System.out.println("✓ Тест обробки колекції пройдено");
    }
    
    // Запуск всіх тестів
    public static void runAllTests() {
        System.out.println("=== ЗАПУСК МОДУЛЬНИХ ТЕСТІВ ===");
        
        testDocumentFactory();
        testPolymorphism();
        testDefaultMethodOverride();
        testSpecificMethods();
        testEdgeCases();
        testCollectionProcessing();
        
        System.out.println("\n=== ВСІ ТЕСТИ УСПІШНО ПРОЙДЕНІ ===");
    }
    
    public static void main(String[] args) {
        // Запуск демонстрації
        DocumentSystemDemo.main(args);
        
        
        runAllTests();
    }