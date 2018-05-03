using System.Collections.Generic;
using System.IO;
using BelgianBeers.Models;
using BelgianBeers.Repositories;

namespace BelgianBeers.Tests.Utilities
{
    public class TestData
    {
        public static string DetermineDataPath(string fileName, string basePath = null)
        {
            if (basePath == null) basePath = Path.GetFullPath(Directory.GetCurrentDirectory());
            var data = Path.Combine(basePath, "data", fileName);

            if (File.Exists(data)) return data;

            return DetermineDataPath(
                basePath: Path.Combine(basePath, ".."),
                fileName: fileName
            );
        }

        public static IEnumerable<(string beerName, string breweryName, double rating, double votes)> Beers
        {
            get
            {
                var sourceData = DetermineDataPath("beerswithnulls.json");
                return BeersStream.FromFile(sourceData);
            }
        }

        public static IEnumerable<Beer> BeerFlow
        {
            get
            {
                var _breweries = new HashSet<Brewery>();
                var _beers = new HashSet<Beer>();

                foreach (var (beerName, breweryName, rating, votes) in Beers)
                {
                    // Store the brewery
                    // TODO DEMO: Null checks here are still a bit ugly
                    var brewery = !string.IsNullOrEmpty(breweryName)
                        ? new Brewery(breweryName)
                        : null;
                
                    if (brewery != null)
                    {
                        _breweries.Add(brewery);
                    }

                    // Store the beer
                    var beer = new Beer(beerName, brewery, rating, votes);
                    _beers.Add(beer);
                }

                return _beers;
            }
        }
    }
}
