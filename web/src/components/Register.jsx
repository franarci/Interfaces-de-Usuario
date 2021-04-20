import '../css/register.css';
//import '../logo.png'

import React from 'react';
import { Link } from 'react-router-dom';
import logo from '../logo.png';
import axios from 'axios';

class Register extends React.Component {
    constructor(props) {
      super(props);

      this.state={
        name:'',
        email:'',
        password:'',
        creditCard:'',
        image: "https://img.favpng.com/5/19/17/cat-computer-icons-user-profile-png-favpng-ssKgUPLWM03x0SY85TD5LsQ5E_t.jpg",  
        isNotValid:false ,
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

      const {name, email, password, creditCard, image} = this.state
      
      axios.post(
        'http://localhost:7000/register',{
          name: name, 
          email: email, 
          password: password,
          creditCard: creditCard, 
          image: image
        }
      
      ).then(response => {
      this.props.handleLogin(response.headers.authentication)
      this.props.history.push(`/`)
      })
      .catch ( error => {
      console.log(error.response.data)
    
      })
     event.preventDefault();
    }


    render(){
      const isNotValid = this.state.isValid;
      let messageAlert;
      if (isNotValid) {
        messageAlert = <p > El usuario ya existe!</p>
      } else {messageAlert= <p></p>}

        return(
     <div className="register">
        <div className="register-body">
          <img src={logo} className="logo" alt="Unqflix logo" />
          <div className="login-form-main">

            <div className= "form-block">
            <h1 className= "completa-tus-datos">Completa tus datos</h1>
            <form onSubmit={this.handleSubmit} className="login-form">
             
        
        <div>{messageAlert}</div> 
             
                    <label className="input_id">
                      <input type="text" 
                      className="name"
                      name= "name" 
                      id="id_userRegister" 
                      autoComplete="name"
                      placeholder="Nombre y Apellido"
                      value= {this.state.name}
                      onChange= {this.handleChange}
                      required/>

                      <h2>Nombre completo</h2>
                    </label>

                    <label className="input_id">
                      <input type="email" 
                      className="email" 
                      name= "email" 
                      id="id_email" 
                      autoComplete="email"
                      placeholder="ejemplo@mail.com"
                      value= {this.state.email}
                      onChange= {this.handleChange}
                      required/>

                      <h2>Email</h2>
                    </label>
                
                  <label className="input_id">
                    <input type="password" 
                    className="password"
                    name= "password"  
                    id="id_password" 
                    autoComplete="password" 
                    placeholder="Contraseña"
                    value= {this.state.password}
                    onChange= {this.handleChange}
                    required/>

                    <h2>Ingrese una nueva contraseña</h2>
                     </label>

                  <label className="input_id">
                    <input type="tel" 
                    className="creditCard" 
                    name= "creditCard" 
                    id="id_cardNumber" 
                    inputMode= "numeric" 
                    autoComplete="creditCard"   
                    maxLength="16" 
                    placeholder="xxxx xxxx xxxx xxxx"
                    value= {this.state.creditCard}
                    onChange= {this.handleChange}
                    required/>
                      
                    <h2>Numero de tarjeta de credito</h2>
                    </label>

                <div>
                  <button type="submit" className="btn btn-danger" autoComplete="off">Registrarse</button>
                  <h6> <Link to="Login">Volver</Link></h6>
                </div> 
                </form>

          </div>
          <div className= "text-body">
              <p className="p-register">Registrate y accede al mejor contenido en peliculas y series </p>
              </div>
        </div>
        </div>
        </div> );
    }
    }

    export default Register;