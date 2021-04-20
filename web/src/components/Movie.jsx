import React from 'react';
import Navbar from './Navbar';
import { Link } from 'react-router-dom';
import '../css/content.css';
import axios from 'axios';
import ContentMiniatura from '../ContentMiniatura.js'

class Movie extends React.Component {
  
  constructor(props) {
    super(props);
    this.state = {
        movie:{
            title: this.props.match.params.title,
            id: '',
            poster: '',
            description: '',
            categories: [],
            relatedContent: [],
            video:"",
            duration:"",
            actors:[],
            directors:[],
        },
        error: '',
    }
    this.handleFavorites=this.handleFavorites.bind(this)
  }

 componentDidMount(){
    
    axios.get(`http://localhost:7000/content/${this.props.match.params.idmovie}`,{ headers:{ Authentication: localStorage.getItem('Token')  }})
    .then(contenido => 
    this.setState({ movie: contenido.data })) 
    .catch(response => this.setState({error: response.data?.title}))
}

handleContent(content){
  var token = localStorage.getItem('Token') 
  if(content.id.charAt(0) === "s"){
   return (<ContentMiniatura to={`/serie/${content.id}`} key={content.id} content={content} />)
  }
  if(content.id.charAt(0) === "m"){
   return (<ContentMiniatura to={`/movie/${content.id}`} key={content.id} content={content}  />)    }
}
handleFavorites(){

  axios({
      method: 'post',
      baseURL: `http://localhost:7000/user/fav/${this.props.match.params.idmovie}`,
      headers: {Authentication: localStorage.getItem('Token')}
    }).then(response => console.log("ok?",response))
    .catch(error => console.log("mal",error.response))    
}

  render(){

    let direcs= "";
    (this.state.movie.directors).forEach(dir=> direcs = direcs +", "+ dir);

    let acts= "";
    (this.state.movie.actors).forEach(ac=> acts = acts +", "+ ac);

    const favButton= "Agregar a favoritos"
    if(this.props.isFavourite){
        favButton= "Quitar de favoritos"
    }
    
    return (
      
        <div className="content">
        
        
        <img className="portada" src={this.state.movie.poster} alt= {this.state.movie.title}/>
        <button className="favs" onClick={this.handleFavorites} type="button">{favButton}</button>
          <div className="tituloYDesc">
            <h1>{this.state.movie.title}</h1>
            <p className="descripcion">{this.state.movie.description}</p>
            <p className="duracion">Duración: {this.state.movie.duration} min</p>
            <p className="actores">Actores: {acts}</p>
            <p className="directores">Dirigida por {direcs}</p>
            <Link to={`/player/${this.state.movie.id}`} className="btn btn-light">Reproducir</Link> 
          </div>

          <div className="related-content">
            <h1 className="h1-rel-cont">Contenido relacionado</h1>
            <div className="content-container">
            {this.state.movie.relatedContent.map( content => (
                this.handleContent(content)
                 ))}
            </div>
           </div>
        
      </div>
    );
}
}

export default Movie;