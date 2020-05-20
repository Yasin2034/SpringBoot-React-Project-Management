import React, { Component } from "react";
import { connect } from "react-redux";
import { login } from "../../actions/securityActions";

class Login extends Component {

    constructor() {
        super()
        this.state = {
            username: "",
            password: "",
            errors: ""
        }
    }

    onChange = e => {
        this.setState({ [e.target.name]: e.target.value });
    }

    onSubmit = e => {
        e.preventDefault()
        const LoginRequest = {
            username: this.state.username,
            password: this.state.password
        }
        this.props.login(LoginRequest)
    }

    componentWillReceiveProps(nextProps) {
        if (nextProps.security.validToken) {
            this.props.history.push("/dashboard")
        }
        if (nextProps.errors) {
            this.setState({ errors: nextProps.errors })
        }
    }

    render() {
        const { errors } = this.state
        return (
            <div className="login">
                <div className="container">
                    <div className="row">
                        <div className="col-md-8 m-auto">
                            <h1 className="display-4 text-center">Log In</h1>
                            <form onSubmit={this.onSubmit}>
                                <div className="form-group">
                                    <input
                                        type="email"
                                        className={errors.username ? "form-control form-control-lg is-invalid" : "form-control form-control-lg"}
                                        placeholder="Email Address"
                                        name="username"
                                        value={this.state.username}
                                        onChange={this.onChange}
                                    />
                                    {errors.username && (<div className="invalid-feedback">{errors.username}</div>)}
                                </div>
                                <div className="form-group">
                                    <input
                                        type="password"
                                        className={errors.password ? "form-control form-control-lg is-invalid" : "form-control form-control-lg"}
                                        placeholder="Password"
                                        name="password"
                                        value={this.state.password}
                                        onChange={this.onChange}
                                    />
                                    {errors.password && (<div className="invalid-feedback">{errors.password}</div>)}
                                </div>
                                <input type="submit" className="btn btn-info btn-block mt-4" />
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        );
    }
}


const mapStateToProps = state => ({
    security: state.security,
    errors: state.errors
})



export default connect(mapStateToProps, { login })(Login);