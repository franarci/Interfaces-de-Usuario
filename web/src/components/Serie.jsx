import React from 'react';
import Navbar from './Navbar';
import '../css/content.css';
import { Link } from 'react-router-dom';
import axios from 'axios';
import ContentMiniatura from '../ContentMiniatura.js'


export default class Serie extends React.Component{
    constructor(props) {
        super(props);
        this.state = {
            serie:{
                title: this.props.match.params.title,
                id: '',
                poster: '',
                description: '',
                seasons: [],
                categories: [],
                relatedContent: []
            },
            error: '',
        }
       this.handleFavorites=this.handleFavorites.bind(this)
    }

    componentDidMount(){
    
        axios.get(`http://localhost:7000/content/${this.props.match.params.idserie}`,{ headers:{ Authentication: localStorage.getItem('Token')  }})
        .then(contenido => 
        this.setState({ serie: contenido.data })) 
        .catch(response => this.setState({error: response.data?.title}))
        
   }
    
    handleFavorites(){

        axios({
            method: 'post',
            baseURL: `http://localhost:7000/user/fav/${this.props.match.params.idserie}`,
            headers: {Authentication: localStorage.getItem('Token')}
          }).then(response => console.log("ok?",response))
          .catch(error => console.log("mal",error.response))    
    }

    handleTemporada(temp){
        console.log(this.state.serie.seasons)
       return (<div>
           <h3>temporada {temp.title} </h3>
           <p>{temp.chapters}</p>
           </div>)
    }

    handleContent(content){
        var token = localStorage.getItem('Token') 
        if(content.id.charAt(0) === "s"){
         return (<ContentMiniatura to={`/serie/${content.id}`} key={content.id} content={content}  />)
        }
        if(content.id.charAt(0) === "m"){
         return (<ContentMiniatura to={`/movie/${content.id}`} key={content.id} content={content}  />)    }
      }
      
    render(){
        const favButton= "Agregar a favoritos"
        if(this.props.isFavourite){
            favButton= "Quitar de favoritos"
        }
        return (
        <div className="content">
            <img className="portada" src={this.state.serie.poster} alt= {this.state.serie.title}/>
            <button className="favs" onClick={this.handleFavorites} type="button">{favButton}</button>
            <div className="tituloYDesc">
                <h1>{this.state.serie.title}</h1>
                <p className="descripcion">{this.state.serie.description}</p>
                <Link to= {`/player/${this.state.serie.id}`} className="btn btn-light">Reproducir</Link> 
            </div>    

            <div className="temporadas"> 
                <h2 className="h2-seasons" id="h2-seasons">Temporadas</h2>
                <div className="temporadas-container">{this.state.serie.seasons.map(temp => {
                   return (
                   <div className="container temporada-container" id="temporada-container">
                       <h3> {temp.title}</h3>
                       <div className="chapter-container">
                           <ul>
                           {temp.chapters.map(
                             chap=> {
                                 return(
                                     <li>
                                    <Link to= {`/player/${this.state.serie.id}`} className="chapter">{chap.title}</Link>
                                    </li>
                                 )
                             }  
                           )}
                           </ul>
                       </div>
                       </div>
                       )         
                    } )}
                     </div>
            </div>

            <div className="related-content">
            <h1 className="h1-rel-cont">Contenido relacionado</h1>
            <div className="content-container">
            {this.state.serie.relatedContent.map( content => (
                this.handleContent(content)
                 ))}
            </div>
           </div>
           </div>
        );
    }
}

