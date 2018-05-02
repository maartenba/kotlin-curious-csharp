using System.Collections.Generic;
using System.IO;
using BelgianBeers.Models;
using BelgianBeers.Repositories;
using JetBrains.Annotations;

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

        public static IEnumerable<Beer> BeerFlow
        {
            get
            {
                var sourceData = TestData.DetermineDataPath("beerswithnulls.json");
                var repository = CreateRepositoryFromFile(sourceData);
                return repository.GetBeers();
            }
        }
        
        private static BeersRepository CreateRepositoryFromFile([PathReference] string file)
        {
            var repository = new BeersRepository();

            foreach (var (beerName, breweryName, rating, votes) in BeersStream.FromFile(file))
            {
                // Store the brewery
                var brewery = !string.IsNullOrEmpty(breweryName)
                    ? new Brewery(breweryName)
                    : null;
                repository.AddBrewery(brewery);

                // Store the beer
                var beer = new Beer(beerName, brewery, rating, votes);
                repository.AddBeer(beer);
            }
            
            return repository;
        }

    }
}