import React, {Component} from 'react';

class ErrorBoundary extends Component{
    constructor(props) {
        super(props);
        this.state = {
            hasError: false,
            error: null,
            info: null
        }
    }

    componentDidCatch(error, info) {
        this.setState({
            hasError: true,
            error: error,
            info: info
        });
    }

    render() {
        if (this.state.hasError) {
            return (
                <div className="card">
                    <h1>Scusami se ti disturbo!(</h1>
                    <h4>Piccolo problema!</h4>
                    <p>The error: {this.state.error.response.data.message}</p>
                    <p>Where it occured: {this.state.info.componentStack}</p>
                </div>
            )
        }
        return this.props.children;
    }
}

export default ErrorBoundary;
