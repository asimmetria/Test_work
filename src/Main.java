import java.util.Scanner;
public class Main {

    public static void main(String[] args) throws CalcException {
        System.out.println("Введите пример:");
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        System.out.println(calc(input));
    }

    public static String calc(String input) throws CalcException {
        String result = null;

        input = input.trim();
// обрезали пробелы

        if (input.isEmpty()) {
            throw new CalcException("Вы ввели пустую строку");
        }
// проверили на пустоту

        String[] parts = input.split(" ");
//разделили на части по пробелу

        if (parts.length!=3) {
            throw new CalcException("формат математической операции не удовлетворяет заданию - два операнда и один оператор (+, -, /, *). Всё через пробел ");
        }
//если частей НЕ 3 - закончили

        String[] arabic = {"10", "1", "2", "3", "4", "5", "6", "7", "8", "9"};
        String idA=null, idB=null;
        int a=0, b=0;
//массив арабских операндов, объявление идентификаторов


        for (int i=0; i<arabic.length; i++) {
            if (arabic[i].equals(parts[0])) {
                a = Integer.parseInt(parts[0]);
                idA ="arabic";
            }
        }
// если первое слагаемое арабское, переведем его в integer запишем в а и присвоем идентификатору idA - "arabic"


        for (int i=0; i<arabic.length; i++) {
            if (arabic[i].equals(parts[2])) {
                b = Integer.parseInt(parts[2]);
                idB ="arabic";
            }
        }
// если второе слагаемое арабское, переведем его в integer запишем в а и присвоем идентификатору idB - "arabic"


        if ((idA!=null)&(idA==idB)) {
            switch (parts[1]){
                case "+":   result=Integer.toString(a+b); break;
                case "-":   result=Integer.toString(a-b); break;
                case "*":   result=Integer.toString(a*b); break;
                case "/":   result=Integer.toString(a/b); break;
            }
        }
// если выполнилось условие в каждом из предыдущих циклов И оба числа арабские, то выполняем вычисление и переводим результат в строку

        if (result!=null) {return result;}
// если получили результат - закончили

        if (idA!=idB) {
            throw new CalcException("Неправильный формат ввода операндов - либо только арабские, либо только римские");
        }
//если один из операндов арабский и лежит от 1 до 10 вкл, а второй - нет, или лежит не от 1 до 10 вкл, заканчиваем


        double res=0;   // Вводим переменную для промежуточного резуьтата вещественного типа поскольку потребуется проверка на резултат деления меньше 1
        if ((isPresent(parts[0]))&(isPresent(parts[2]))) {        // если операнды входят в перечесление римских чисел
            a = Roman.valueOf(parts[0]).arabic;                   // то а присваваем арабский эквивалент первого операнда
            b = Roman.valueOf(parts[2]).arabic;                     // то b присваваем арабский эквивалент второго операнда

            switch (parts[1]){                                        // пербрали оператор, поcчитали выражение, получили ответ Integer.
                case "+":   res=a+b; break;
                case "-":   res=a-b; break;
                case "*":   res=a*b; break;
                case "/":   res=a/b; break;
            }
            if (res<1) {
                throw new CalcException("Результат вычисления меньше 1"); // тут вопрос по условию: в пункте 10 сказано, что если результат работы с римскими цифрами меньше 1 , то выбрасывается исключение
            }                                                              // для этого пришлось учитвать, что реультат деления мб меньше 1 - тоже будет исключение
            else {
                int r= (int) res; // приводим промежуточный результат к Int'у
                result=ConvertIntToRoman(r);
            }

        }


        if (result==null) {
            throw new CalcException("Неправильный формат ввода операндов - либо только арабские, либо только римские");
        }

    return result;

    }

    enum Roman { // перечисление римских чисел
        I(1), II(2), III(3), IV(4), V(5), VI(6), VII(7), VIII(8), IX(9), X(10);
        private int arabic;
        Roman(int arabic) {
            this.arabic = arabic;
        }
    }

    static boolean isPresent(String data) { // метод проверки переменной на совпадение в перечислении Roman - подсмотрел на хабре
        try {
            Enum.valueOf(Roman.class, data);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }

    }

    static String ConvertIntToRoman(int n) // конвертация арабских в римские - нашел на  гитхабе (лишнее удалил, наш результат не больше 100) https://gist.github.com/unilecs/0ed50d630f35acf291e458c26e9bd33e
    {
        if (n >= 100)
            return n >= 400 ? ("CD" + ConvertIntToRoman(n - 400)) : ("C" + ConvertIntToRoman(n - 100));

        if (n >= 50)
            return n >= 90 ? ("XC" + ConvertIntToRoman(n - 90)) : ("L" + ConvertIntToRoman(n - 50));

        if (n >= 10)
            return n >= 40 ? ("XL" + ConvertIntToRoman(n - 40)): ("X" + ConvertIntToRoman(n - 10));

        if (n >= 5)
            return n == 9 ? "IX" : "V" + ConvertIntToRoman(n - 5);

        if (n > 0)
            return n == 4 ? "IV" : "I" + ConvertIntToRoman(n - 1);

        return "";
    }


   static class CalcException extends Exception {  //класс и метод для выбрасывания своих исключений, насколько яч понимаю, здесь нарушил условие задачи:
                                                    // "Добавленные классы не должны иметь модификаторы доступа (public или другие),
                                                    // но не знаю как по другому работать с исключениями, или объявить этот кдласс без "static"

        public CalcException(String description){
            super(description);
        }
    }

}

