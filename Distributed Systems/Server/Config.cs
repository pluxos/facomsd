using Microsoft.Extensions.Configuration;
using System.IO;

namespace Server
{
    public static class Config
    {
        public static int Port()
        {
            var builder = new ConfigurationBuilder()
                .SetBasePath(Directory.GetCurrentDirectory())
                .AddJsonFile("appsettings.json", optional: true, reloadOnChange: true);

            IConfigurationRoot configuration = builder.Build();
            return int.Parse(configuration.GetSection("Port").Value);
        }
    }
}
