import React, {Component} from 'react';
import AuthenticationService from '../../common/services/AuthenticationService'

class LoginPage extends Component{

    constructor(props) {
        super(props);
        this.state = {
            username: 'initex',
            password: 'initex000',
            hasLoginFailed: false,
            showSuccessMessage: false
        };
        this.handleChange = this.handleChange.bind(this);
        this.loginClicked = this.loginClicked.bind(this);
    }

    handleChange(event) {
        this.setState(
            {
                [event.target.name]: event.target.value
            }
        )
    }

    loginClicked() {
        AuthenticationService
            .executeBasicAuthenticationService(this.state.username, this.state.password)
            .then(response => {
                if (response.data.message === "ADMIN") {
                    AuthenticationService.registerSuccessfulLogin(this.state.username, this.state.password);
                    this.props.history.push('/admin/page')
                } else {
                    AuthenticationService.registerSuccessfulLogin(this.state.username, this.state.password);
                    this.props.history.push('/user/page');
                }
            }).catch(() => {
            this.setState({showSuccessMessage: false});
            this.setState({hasLoginFailed: true});
        })
    }

    render() {
        return (
            <div>
            <h1>Login</h1>
        <div className='container'>
            {this.state.hasLoginFailed && <div className='alert alert-warning'>Invalid Credentials</div>}
            {this.state.showSuccessMessage && <div>login Successful</div>}
            User Name: <input type='text' name='username'
                              onChange={this.handleChange}/>
            Password: <input type='password' name='password'
                             onChange={this.handleChange}/>
            <button className='button' onClick={this.loginClicked}> Login</button>
        </div>
        </div>
        )
    }
}
export default LoginPage



