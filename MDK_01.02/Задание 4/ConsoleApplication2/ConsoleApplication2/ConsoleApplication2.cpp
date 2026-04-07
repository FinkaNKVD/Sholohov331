#include <iostream>
#include <limits>
#include "calculator.h"

namespace {
    void clearLine() {
        std::cin.clear();
        std::cin.ignore(std::numeric_limits<std::streamsize>::max(), '\n');
    }

    bool readInt(int& out) {
        if (!(std::cin >> out)) {
            clearLine();
            return false;
        }
        return true;
    }

    bool readDouble(double& out) {
        if (!(std::cin >> out)) {
            clearLine();
            return false;
        }
        return true;
    }

    void printMenu() {
        std::cout << "\n=== КАЛЬКУЛЯТОР ===\n";
        std::cout << "1. Сложение (+)\n";
        std::cout << "2. Вычитание (-)\n";
        std::cout << "3. Умножение (*)\n";
        std::cout << "4. Деление (/)\n";
        std::cout << "5. Возведение в степень (^)\n";
        std::cout << "6. Квадратный корень (√)\n";
        std::cout << "7. Факториал (!)\n";
        std::cout << "8. Сохранить в память (M+)\n";
        std::cout << "9. Извлечь из памяти (MR)\n";
        std::cout << "10. Добавить к памяти (M+ value)\n";
        std::cout << "11. Очистить память (MC)\n";
        std::cout << "0. Выход\n";
        std::cout << "Выберите операцию: ";
    }
}

int main() {
    Calculator calc;
    int choice = 0;
    double a = 0.0, b = 0.0, result = 0.0;

    while (true) {
        printMenu();
        if (!readInt(choice)) {
            std::cout << "Неверный выбор. Попробуйте снова.\n";
            continue;
        }

        if (choice == 0) break;

        try {
            switch (choice) {
            case 1:
                std::cout << "a + b = ?\nВведите a и b: ";
                if (!readDouble(a) || !readDouble(b)) break;
                result = calc.add(a, b);
                std::cout << a << " + " << b << " = " << result << std::endl;
                break;
            case 2:
                std::cout << "a - b = ?\nВведите a и b: ";
                if (!readDouble(a) || !readDouble(b)) break;
                result = calc.subtract(a, b);
                std::cout << a << " - " << b << " = " << result << std::endl;
                break;
            case 3:
                std::cout << "a * b = ?\nВведите a и b: ";
                if (!readDouble(a) || !readDouble(b)) break;
                result = calc.multiply(a, b);
                std::cout << a << " * " << b << " = " << result << std::endl;
                break;
            case 4:
                std::cout << "a / b = ?\nВведите a и b: ";
                if (!readDouble(a) || !readDouble(b)) break;
                result = calc.divide(a, b);
                std::cout << a << " / " << b << " = " << result << std::endl;
                break;
            case 5:
                std::cout << "base ^ exp = ?\nВведите основание и степень: ";
                if (!readDouble(a) || !readDouble(b)) break;
                result = calc.power(a, b);
                std::cout << a << " ^ " << b << " = " << result << std::endl;
                break;
            case 6:
                std::cout << "√x = ?\nВведите x: ";
                if (!readDouble(a)) break;
                result = calc.sqrt(a);
                std::cout << "√" << a << " = " << result << std::endl;
                break;
            case 7:
                std::cout << "n! = ?\nВведите целое неотрицательное n: ";
                {
                    int n = 0;
                    if (!readInt(n)) break;
                    std::cout << n << "! = " << calc.factorial(n) << std::endl;
                }
                break;
            case 8:
                std::cout << "Сохранить число в память.\nВведите число: ";
                if (!readDouble(a)) break;
                calc.memorySave(a);
                std::cout << "Число " << a << " сохранено в памяти.\n";
                break;
            case 9:
                result = calc.memoryRecall();
                std::cout << "Число в памяти: " << result << std::endl;
                break;
            case 10:
                std::cout << "Добавить число к памяти.\nВведите число: ";
                if (!readDouble(a)) break;
                calc.memoryAdd(a);
                std::cout << "К памяти прибавлено " << a << ". Теперь в памяти: " << calc.memoryRecall() << std::endl;
                break;
            case 11:
                calc.memoryClear();
                std::cout << "Память очищена.\n";
                break;
            default:
                std::cout << "Неверный выбор. Попробуйте снова.\n";
            }
        }
        catch (const std::exception& e) {
            std::cout << "Ошибка: " << e.what() << std::endl;
        }

        // Очистка потока ввода после возможного ошибочного ввода
        clearLine();
    }

    std::cout << "До свидания!\n";
    return 0;
}