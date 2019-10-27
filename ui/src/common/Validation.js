class Validation{

    validateInput(id, message, type) {
        if (this.rules(id, type)) {
            document.getElementById(id).className = "col-width-height";
            message('', '');
            return {isValid: true, errorField: id};
        } else {
            document.getElementById(id).className = "input-field-error-state";
            message('error', (id + ' field is empty!'));
            return {isValid: false, errorField: id};
        }
    }

    rules(id, type) {
        switch (type) {
            case 'text':
                return document.getElementById(id).value !== '';
            case "number":
                return !isNaN(document.getElementById(id).value) && document.getElementById(id).value !== '';
        }
    }

    validateForm(list, message) {
        let check = {isValid: true, errors: []};
        list.forEach(item => {
            let filedCheck = this.validateInput(item.id, message, item.type);
            if (filedCheck.isValid === false) {
                check.isValid = false;
                check.errors.push(filedCheck.errorField);
            }
        });
        check.errors.forEach(item => {
            message('error', (item + ' field is empty!'));
        });
        return check.isValid;
    }
}

export default new Validation();