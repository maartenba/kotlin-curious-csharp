using System;
using System.IO;
using System.Linq;
using System.Threading.Tasks;
using BelgianBeers.Models;
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

            // Get beers with a rating > .50, and at least 10 votes for relevance (LINQ DSL)
            var beersWithOkayRatingDsl = from beer in repository.GetBeers()
                where beer.Rating > .50 && beer.Votes >= 10
                select beer;
            
            // Get beers with a rating > .50, and at least 10 votes for relevance
            var beersWithOkayRating = repository.GetBeers()
                .Where(beer => beer.Rating > .50 && beer.Votes >= 10)
                .ToList();
            
            // TODO DEMO: So many allocations - check in IL, mention https://github.com/antiufo/roslyn-linq-rewrite
            
            // Get beers that are from brewery "Westmalle"
            // TODO DEMO: Needs null check - Brewery property can be null (use annotation so IDE warns us)
            var westmalleBeers = repository.GetBeers()
                .Where(beer => string.Equals(beer.Brewery.Name, "Brouwerij der Trappisten van Westmalle", StringComparison.OrdinalIgnoreCase))
                .ToList();
            
            // Pattern matching (on a property, not on type):
            
            foreach (var westmalleBeer in westmalleBeers)
            {
                switch (westmalleBeer)
                {
                    case DubbelBeer dubbelBeer when westmalleBeer.Name.IndexOf("dubbel", StringComparison.OrdinalIgnoreCase) >= 0:
                        // It is a dubbel
                        Console.WriteLine(dubbelBeer.Name);
                        break;
                    
                    case TripelBeer tripelBeer when westmalleBeer.Name.IndexOf("tripel", StringComparison.OrdinalIgnoreCase) >= 0:
                        // It is a tripel
                        Console.WriteLine(tripelBeer.Name);
                        break;
                }
            }
            
            // TODO: Find an API that returns something around beer, so we can e.g. fetch images. Maybe Google Image API? Other?
        }

        static string DetermineDataPath(string fileName, string basePath = null)
        {
            if (basePath == null) basePath = Path.GetFullPath(Directory.GetCurrentDirectory());
            var data = Path.Combine(basePath, "data", fileName);

            if (File.Exists(data)) return data;

            return DetermineDataPath(
                basePath: Path.Combine(basePath, ".."),
                fileName: fileName
            );
        }
    }
}
