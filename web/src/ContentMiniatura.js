import React from 'react';
import { Link } from 'react-router-dom';


function ContentMiniatura ({content, to, token, key, isFavourite}){
    
  const {title, poster}= content;

        if (content.id.charAt(0) === "s"){          
           

          return  (  <div className="serie">
          <Link to={to} isFavourite={isFavourite} >
         <img src={poster} className="poster img-fluid" alt={title}/>
         </Link>
         
     </div>)
        }
        if (content.id.charAt(0) === "m"){          
          
        return  (  <div className="movie">
        <Link to={to} >
       <img src={poster} className="poster img-fluid" alt={title}/>
       </Link>      
   </div>)
      }
      
}

export default ContentMiniatura;