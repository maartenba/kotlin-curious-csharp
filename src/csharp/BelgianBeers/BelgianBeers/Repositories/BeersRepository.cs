using System;
using System.Collections.Generic;
using System.Linq;
using BelgianBeers.Models;

namespace BelgianBeers.Repositories
{
    public class BeersRepository
    {
        private readonly HashSet<Brewery> _breweries = new HashSet<Brewery>();
        private readonly HashSet<Beer> _beers = new HashSet<Beer>();
        
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