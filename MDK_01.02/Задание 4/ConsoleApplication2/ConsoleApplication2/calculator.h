#ifndef CALCULATOR_H
#define CALCULATOR_H

#include <cmath>
#include <stdexcept>

class Calculator {
private:
    double memory;

public:
    Calculator();

    // Базовые операции
    double add(double a, double b) const;
    double subtract(double a, double b) const;
    double multiply(double a, double b) const;
    double divide(double a, double b) const;

    // Возведение в степень
    double power(double base, double exp) const;

    // Квадратный корень
    double sqrt(double x) const;

    // Факториал (только для целых неотрицательных чисел)
    long long factorial(int n) const;

    // Операции с памятью
    void memorySave(double value);
    double memoryRecall() const;
    void memoryAdd(double value);
    void memoryClear();
};

#endif