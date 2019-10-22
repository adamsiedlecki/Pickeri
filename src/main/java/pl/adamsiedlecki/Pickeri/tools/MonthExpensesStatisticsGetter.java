package pl.adamsiedlecki.Pickeri.tools;

import pl.adamsiedlecki.Pickeri.entity.Expense;
import pl.adamsiedlecki.Pickeri.pojos.MonthExpanses;
import pl.adamsiedlecki.Pickeri.service.ExpenseService;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class MonthExpensesStatisticsGetter {

    private ExpenseService expenseService;

    public static List<MonthExpanses> getMontExpansesStatistics(ExpenseService expenseService) {
        Map<Integer, String> months = getMonths();
        if (months.size() != 12) {
            return List.of();
        }
        List<MonthExpanses> result = new ArrayList<>();
        BigDecimal allSum = expenseService.getTotalAmountOfSpentMoney();
        for (int i = 1; i <= months.size(); i++) {
            BigDecimal sum = new BigDecimal(0);
            List<Expense> expenses = expenseService.findAllWithFilter();
            for (Expense expense : expenses) {
                if (expense.getTime().getMonthValue() == i) {
                    sum = sum.add(expense.getMoneyAmount());
                }
            }
            BigDecimal percentage = sum.divide(allSum, 2, RoundingMode.FLOOR).multiply(new BigDecimal(100));
            result.add(new MonthExpanses(sum, months.get(i), percentage));
        }
        return result;
    }

    private static Map<Integer, String> getMonths() {
        Map<Integer, String> months = new TreeMap<>();
        months.put(1, "Styczeń");
        months.put(2, "Luty");
        months.put(3, "Marzec");
        months.put(4, "Kwiecień");
        months.put(5, "Maj");
        months.put(6, "Czerwiec");
        months.put(7, "Lipiec");
        months.put(8, "Sierpień");
        months.put(9, "Wrzesień");
        months.put(10, "Październik");
        months.put(11, "Listopad");
        months.put(12, "Grudzień");
        return months;
    }

}
