using System.Collections.Generic;
using System.IO;
using BelgianBeers.Models;
using JetBrains.Annotations;
using Newtonsoft.Json;
using Newtonsoft.Json.Linq;

namespace BelgianBeers.Repositories
{
    public static class BeersStream
    {
        public static IEnumerable<(string beerName, string breweryName, double rating, double votes)> FromFile([PathReference] string file)
        {
            if (!File.Exists(file))
            {
                throw new FileNotFoundException("Data file not found.", file);
            }

            using (var reader = new JsonTextReader(new StreamReader(File.OpenRead(file))))
            {
                while (reader.Read())
                {
                    if (reader.TokenType == JsonToken.StartObject)
                    {
                        // Load data from the stream
                        var beerData = JObject.Load(reader);

                        var breweryName = beerData.Value<string>("brewery");
                        var beerName = beerData.Value<string>("name");
                        var rating = beerData.Value<double>("rating");
                        var votes = beerData.Value<double>("votes");

                        yield return (beerName, breweryName, rating, votes);
                    }
                }
            }
        }
    }
}
