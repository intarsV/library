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
        switch (type) {
            case 'text':
                return document.getElementById(id).value !== '';
            case "number":
                return !isNaN(document.getElementById(id).value) && document.getElementById(id).value !== '';
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