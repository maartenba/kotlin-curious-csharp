using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Threading.Tasks;
using BelgianBeers.Models;
using JetBrains.Annotations;
using Newtonsoft.Json;
using Newtonsoft.Json.Linq;

namespace BelgianBeers.Repositories
{
    public class BeersRepository
    {
        private readonly HashSet<Brewery> _breweries = new HashSet<Brewery>();
        private readonly HashSet<Beer> _beers = new HashSet<Beer>();
        
        public static async Task<BeersRepository> FromFile([PathReference] string file)
        {            
            if (!File.Exists(file))
            {
                throw new FileNotFoundException("Data file not found.", file);
            }
            
            var repository = new BeersRepository();
            
            using (var reader = new JsonTextReader(new StreamReader(File.OpenRead(file))))
            {
                while (await reader.ReadAsync())
                {
                    if (reader.TokenType == JsonToken.StartObject)
                    {
                        // Load data from the stream
                        var beerData = JObject.Load(reader);

                        var breweryName = beerData.Value<string>("brewery");
                        var beerName = beerData.Value<string>("name");
                        var rating = beerData.Value<double>("rating");
                        var votes = beerData.Value<double>("votes");
                        
                        #region Bad approach - No nulls in data
                        
                        // Store the brewery
                        // TODO DEMO: This get/add pattern is stupid! Add GetHashCode to Brewery() and just call .Add on the underlying hashset
                        var brewery = repository.GetBrewery(breweryName);
                        if (brewery == null)
                        {
                            brewery = new Brewery(breweryName);
                            repository.AddBrewery(brewery);
                        }
                        
                        // Store the beer
                        // TODO DEMO: This get/add is needed to ensure no duplicates, however we could again do a GetHashCode() instead
                        var beer = repository.GetBeer(beerName);
                        if (beer == null)
                        {
                            beer = new Beer(beerName, brewery, rating, votes);
                            repository.AddBeer(beer);
                        }
                        
                        #endregion
                        
                        #region Bad approach - Nulls in data
                        
//                        // Store the brewery
//                        // TODO DEMO: This get/add pattern is stupid! Add GetHashCode to Brewery() and just call .Add on the underlying hashset
//                        // TODO DEMO: Null checks here are ugly, quick. Does Kotlin have anything better?
//                        Brewery brewery =  null;
//                        if (!string.IsNullOrEmpty(breweryName))
//                        {
//                            brewery = repository.GetBrewery(breweryName);
//                            if (brewery == null)
//                            {
//                                brewery = new Brewery(breweryName);
//                                repository.AddBrewery(brewery);
//                            }
//                        }
//                        
//                        // Store the beer
//                        // TODO DEMO: This get/add is needed to ensure no duplicates, however we could again do a GetHashCode() instead
//                        var beer = repository.GetBeer(beerName);
//                        if (beer == null)
//                        {
//                            beer = new Beer(beerName, brewery, rating, votes);
//                            repository.AddBeer(beer);
//                        }
                        
                        #endregion
                        
                        #region Good approach
                        
//                        // Store the brewery
//                        // TODO DEMO: Null checks here are ugly, quick. Does Kotlin have anything better?
//                        var brewery = !string.IsNullOrEmpty(breweryName)
//                            ? new Brewery(breweryName)
//                            : null;
//                        repository.AddBrewery(brewery);
//                        
//                        // Store the beer
//                        var beer = new Beer(beerName, brewery, rating, votes);
//                        repository.AddBeer(beer);
                        
                        #endregion
                    }
                }
            }

            return repository;
        }

        public Brewery GetBrewery(string breweryName)
        {
            return _breweries.FirstOrDefault(
                brewery => string.Equals(brewery.Name, breweryName, StringComparison.OrdinalIgnoreCase));
        }

        public IEnumerable<Brewery> GetBreweries()
        {
            return _breweries;
        }

        public void AddBrewery(Brewery brewery)
        {
            if (brewery == null) return;
            _breweries.Add(brewery);
        }

        public Beer GetBeer(string beerName)
        {
            return _beers.FirstOrDefault(
                beer => string.Equals(beer.Name, beerName, StringComparison.OrdinalIgnoreCase));
        }

        public IEnumerable<Beer> GetBeers()
        {
            return _beers;
        }

        public void AddBeer(Beer beer)
        {
            if (beer.Name.IndexOf("dubbel", StringComparison.OrdinalIgnoreCase) >= 0)
            {
                beer = new DubbelBeer(beer.Name, beer.Brewery, beer.Rating, beer.Votes);
            }
            else if (beer.Name.IndexOf("tripel", StringComparison.OrdinalIgnoreCase) >= 0)
            {
                beer = new TripelBeer(beer.Name, beer.Brewery, beer.Rating, beer.Votes);
            }
            
            _beers.Add(beer);
        }
    }
}