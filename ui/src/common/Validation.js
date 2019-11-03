class Validation{

    validateInput(id, type, setInfoMessage) {
        setInfoMessage('', '');
        if (this.rules(id, type)) {
            document.getElementById(id).className = "input-field";
            document.getElementById(id + "_error").className = "error-field-hide";
            return true;
        } else {
            document.getElementById(id).className = "input-field-error-state";
            document.getElementById(id + "_error").className = "error-field-show";
            return false;
        }
    }

    rules(id, type) {
        const testValue = document.getElementById(id).value;
        const textRegEx = /^[a-zA-Z]+$/;
        const passwordRegex = /^[a-zA-Z0-9]+$/;
        switch (type) {
            case 'text':
                return testValue !== '' && textRegEx.test(testValue);
            case 'number':
                return !isNaN(testValue) && testValue !== '';
            case 'reg-login':
                return testValue !== '' && testValue.length >= 3 && textRegEx.test(testValue);
            case 'reg-password':
                return testValue !== '' && testValue.length >= 3 && passwordRegex.test(testValue);
        }
    }

    validateForm(list, setInfoMessage) {
        let check = true;
        list.forEach(item => {
            if (this.validateInput(item.id, item.type, setInfoMessage) === false) {
                check = false;
            }
        });
        return check;
    }
}

export default new Validation();