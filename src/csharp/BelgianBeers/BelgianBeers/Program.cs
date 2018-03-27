using System;
using System.IO;
using System.Linq;
using System.Threading.Tasks;
using BelgianBeers.Repositories;

namespace BelgianBeers
{
    class Program
    {
        static async Task Main(string[] args)
        {
            var sourceData = DetermineDataPath("beerswithnulls.json");

            var repository = await BeersRepository.FromFile(sourceData);
            
            // Filtering data:
            // Get beers with a rating > .50, and at least 10 votes for relevance
            var beersWithOkayRating = repository.GetBeers()
                .Where(beer => beer.Rating > .50 && beer.Votes >= 10)
                .ToList();
            
            // Get beers that are from brewery "Westmalle"
            // TODO DEMO: Needs null check - Brewery property can be null (use annotation so IDE warns us)
            var westmalleBeers = repository.GetBeers()
                .Where(beer => string.Equals(beer.Brewery.Name, "Brouwerij der Trappisten van Westmalle", StringComparison.OrdinalIgnoreCase))
                .ToList();
            
            // TODO: Find an API that returns something around beer, so we can e.g. fetch images. Maybe Google Image API? Other?
        }

        private static string DetermineDataPath(string fileName)
        {
            var sourceData = Path.GetFullPath("..\\..\\..\\..\\data\\" + fileName);
            if (!File.Exists(sourceData))
            {
                throw new FileNotFoundException("Data file not found.");
            }

            return sourceData;
        }
    }
}
