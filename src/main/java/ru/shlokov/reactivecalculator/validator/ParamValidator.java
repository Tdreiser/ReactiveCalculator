package ru.shlokov.reactivecalculator.validator;


import org.python.core.PyException;
import org.python.util.PythonInterpreter;
import org.springframework.stereotype.Component;

@Component
public class ParamValidator {

    public boolean isValidFunction(String script) {
        try (PythonInterpreter pythonInterpreter = new PythonInterpreter()) {
            pythonInterpreter.exec(script);
        } catch (PyException err) {
            return false;
        }
        return true;
    }

    public boolean isValidParameter(long numberOfCalculates) {
        if (numberOfCalculates < 1) {
            return false;
        }
        return true;
    }


}

