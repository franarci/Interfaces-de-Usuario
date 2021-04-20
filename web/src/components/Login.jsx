import '../css/login.css';
import '../logo.png'

import React from 'react';
import { Link } from 'react-router-dom';
import logo from '../logo.png';
import axios from 'axios';


class Login extends React.Component {
    constructor(props) {
      super(props);
      
      this.state = {
        email : '',
        password : '',
        error: ''

      }

      this.handleSubmit = this.handleSubmit.bind(this)
      this.handleChange = this.handleChange.bind(this)
    }

    handleChange(event){
      this.setState({
       [event.target.name]: event.target.value
      })
    }

    handleSubmit(event){
      const {email, password} = this.state
  

      axios.post(
        'http://localhost:7000/login',{
          
            email : email,
            password : password
          
        }
      )
      .then(response => {
        this.props.handleLogin(response.headers.authentication)
        this.props.history.push(`/`)
      })
      .catch(error => {
        console.log(error.response.data)
       })
       event.preventDefault();
    }

    render(){
      localStorage.removeItem('Token')
        return(
        <div className="login">
        <div className="login-body">
          <img src={logo} className="logo-login" alt="logo" />
          <div className="login-form-main">

            <h1 className="iniciarSesion">Iniciar Sesion</h1>
            <form onSubmit ={this.handleSubmit} className="login-form">
             
                    <label className="input_id">
                      <input type="email"
                             name="email"
                             className="email" 
                             id="id_userLogin" 
                             autoComplete="email" 
                             placeholder="Email"
                             value= {this.state.email}
                             onChange= {this.handleChange}
                             required/>
                    </label>
                
                    <label className="input_id">
                      <input type="password"
                           name= "password"
                           className="password"
                           id="id_password"
                           autoComplete="password" 
                           placeholder="Contraseña"
                           value= {this.state.password}
                           onChange= {this.handleChange}
                           required/>
                    </label>
                    <div>
                      <button type="submit" className="btn btn-danger" autoComplete="off">Iniciar Sesión</button>
                      <h6>¿Aún no estás registrado? <Link to="Register">Regístrate ahora</Link></h6>
                    </div> 
            </form> 
          </div>
        </div>
      </div> );
    }
  }

    export default Login;